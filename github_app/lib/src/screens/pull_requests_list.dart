import 'package:github_app/src/query_exception.dart';
import 'package:github_app/src/github_gql/github_gql.dart';
import 'package:flutter/material.dart';
import 'package:gql_exec/gql_exec.dart';
import 'package:gql_http_link/gql_http_link.dart';
import 'package:url_launcher/url_launcher.dart';

class PullRequestsList extends StatefulWidget {
  const PullRequestsList({Key? key, required this.link}) : super(key: key);

  final HttpLink link;

  @override
  _PullRequestsListState createState() => _PullRequestsListState();
}

class _PullRequestsListState extends State<PullRequestsList> {
  late Future<List<GPullRequestsData_viewer_pullRequests_edges_node>>
      _pullRequests;

  @override
  void initState() {
    super.initState();
    _pullRequests = _retrievePullRequests(widget.link);
  }

  Future<List<GPullRequestsData_viewer_pullRequests_edges_node>>
      _retrievePullRequests(HttpLink link) async {
    final GPullRequests _request = GPullRequests(
        (GPullRequestsBuilder builder) => builder..vars.count = 100);
    final Response _result = await link
        .request(Request(
          operation: _request.operation,
          variables: _request.vars.toJson(),
        ))
        .first;
    final List<GraphQLError>? _errors = _result.errors;
    if (_errors != null && _errors.isNotEmpty) {
      throw QueryException(_errors);
    }
    return GPullRequestsData.fromJson(_result.data!)!
        .viewer
        .pullRequests
        .edges!
        .map((GPullRequestsData_viewer_pullRequests_edges e) => e.node)
        .whereType<GPullRequestsData_viewer_pullRequests_edges_node>()
        .toList();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<
        List<GPullRequestsData_viewer_pullRequests_edges_node>>(
      future: _pullRequests,
      builder: (_,
          AsyncSnapshot<List<GPullRequestsData_viewer_pullRequests_edges_node>>
              snapshot) {
        if (snapshot.hasError) {
          return Center(child: Text('${snapshot.error}'));
        }
        if (!snapshot.hasData) {
          return const Center(child: CircularProgressIndicator.adaptive());
        }
        final List<GPullRequestsData_viewer_pullRequests_edges_node>
            _pullRequests = snapshot.data!;
        return ListView.builder(
          primary: false,
          itemCount: _pullRequests.length,
          prototypeItem: const SizedBox(height: 60),
          padding: const EdgeInsets.symmetric(vertical: 8),
          itemBuilder: (_, int index) {
            final GPullRequestsData_viewer_pullRequests_edges_node
                _pullRequest = _pullRequests[index];
            return ListTile(
              title: Text(_pullRequest.title),
              subtitle: Text('${_pullRequest.repository.nameWithOwner} '
                  'PR #${_pullRequest.number} '
                  'opened by ${_pullRequest.author!.login} '
                  '(${_pullRequest.state.name.toLowerCase()})'),
              onTap: () async {
                final url = _pullRequest.url.value;
                if (await canLaunch(url)) await launch(url);
              },
            );
          },
        );
      },
    );
  }
}
