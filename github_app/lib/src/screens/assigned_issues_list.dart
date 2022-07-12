import 'package:github_app/src/query_exception.dart';
import 'package:github_app/src/github_gql/github_gql.dart';
import 'package:flutter/material.dart';
import 'package:gql_exec/gql_exec.dart';
import 'package:gql_http_link/gql_http_link.dart';
import 'package:url_launcher/url_launcher.dart';

class AssignedIssuesList extends StatefulWidget {
  const AssignedIssuesList({Key? key, required this.link}) : super(key: key);

  final HttpLink link;

  @override
  _AssignedIssuesListState createState() => _AssignedIssuesListState();
}

class _AssignedIssuesListState extends State<AssignedIssuesList> {
  late Future<List<GAssignedIssuesData_search_edges_node__asIssue>>
      _assignedIssues;

  @override
  void initState() {
    super.initState();
    _assignedIssues = _retrieveAssignedissues(widget.link);
  }

  Future<List<GAssignedIssuesData_search_edges_node__asIssue>>
      _retrieveAssignedissues(HttpLink link) async {
    final GViewerDetail _request =
        GViewerDetail((GViewerDetailBuilder builder) => builder);
    Response _result = await link
        .request(Request(
          operation: _request.operation,
          variables: _request.vars.toJson(),
        ))
        .first;
    List<GraphQLError>? _errors = _result.errors;
    if (_errors != null && _errors.isNotEmpty) {
      throw QueryException(_errors);
    }
    final GViewerDetailData_viewer _viewer =
        GViewerDetailData.fromJson(_result.data!)!.viewer;
    final GAssignedIssues _issueRequest = GAssignedIssues(
      (GAssignedIssuesBuilder builder) => builder
        ..vars.count = 100
        ..vars.query = 'is:open assignee:${_viewer.login} archived:false',
    );
    _result = await link
        .request(Request(
          operation: _issueRequest.operation,
          variables: _issueRequest.vars.toJson(),
        ))
        .first;
    _errors = _result.errors;
    if (_errors != null && _errors.isNotEmpty) {
      throw QueryException(_errors);
    }
    return GAssignedIssuesData.fromJson(_result.data!)!
        .search
        .edges!
        .map((GAssignedIssuesData_search_edges e) => e.node)
        .whereType<GAssignedIssuesData_search_edges_node__asIssue>()
        .toList();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List<GAssignedIssuesData_search_edges_node__asIssue>>(
      future: _assignedIssues,
      builder: (_,
          AsyncSnapshot<List<GAssignedIssuesData_search_edges_node__asIssue>>
              snapshot) {
        if (snapshot.hasError) {
          return Center(child: Text('${snapshot.error}'));
        }
        if (!snapshot.hasData) {
          return const Center(child: CircularProgressIndicator.adaptive());
        }
        final List<GAssignedIssuesData_search_edges_node__asIssue>
            _assignedIssues = snapshot.data!;
        return ListView.builder(
          primary: false,
          itemCount: _assignedIssues.length,
          prototypeItem: const SizedBox(height: 60),
          padding: const EdgeInsets.symmetric(vertical: 8),
          itemBuilder: (_, int index) {
            final GAssignedIssuesData_search_edges_node__asIssue
                _assignedIssue = _assignedIssues[index];
            return ListTile(
              title: Text(_assignedIssue.title),
              subtitle: Text('${_assignedIssue.repository.nameWithOwner} '
                  'Issue #${_assignedIssue.number} '
                  'opened by ${_assignedIssue.author!.login}'),
              onTap: () async {
                final url = _assignedIssue.url.value;
                if (await canLaunch(url)) await launch(url);
              },
            );
          },
        );
      },
    );
  }
}
