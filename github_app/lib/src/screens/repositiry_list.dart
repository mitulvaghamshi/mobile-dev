import 'package:github_app/src/query_exception.dart';
import 'package:github_app/src/github_gql/github_gql.dart';
import 'package:flutter/material.dart';
import 'package:gql_exec/gql_exec.dart';
import 'package:gql_http_link/gql_http_link.dart';
import 'package:url_launcher/url_launcher.dart';

class RepositoryList extends StatefulWidget {
  const RepositoryList({Key? key, required this.link}) : super(key: key);

  final HttpLink link;

  @override
  _RepositoryListState createState() => _RepositoryListState();
}

class _RepositoryListState extends State<RepositoryList> {
  late Future<List<GRepositoriesData_viewer_repositories_nodes>> _repositories;

  @override
  void initState() {
    super.initState();
    _repositories = _retrieveRepositories(widget.link);
  }

  Future<List<GRepositoriesData_viewer_repositories_nodes>>
      _retrieveRepositories(HttpLink link) async {
    final GRepositories _request = GRepositories(
        (GRepositoriesBuilder builder) => builder..vars.count = 100);
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
    return GRepositoriesData.fromJson(_result.data!)!
        .viewer
        .repositories
        .nodes!
        .asList();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List<GRepositoriesData_viewer_repositories_nodes>>(
      future: _repositories,
      builder: (_,
          AsyncSnapshot<List<GRepositoriesData_viewer_repositories_nodes>>
              snapshot) {
        if (snapshot.hasError) {
          return Center(child: Text('${snapshot.error}'));
        }
        if (!snapshot.hasData) {
          return const Center(child: CircularProgressIndicator.adaptive());
        }
        final List<GRepositoriesData_viewer_repositories_nodes> _repositories =
            snapshot.data!;
        return ListView.builder(
          primary: false,
          itemCount: _repositories.length,
          prototypeItem: const SizedBox(height: 60),
          padding: const EdgeInsets.symmetric(vertical: 8),
          itemBuilder: (_, int index) {
            final GRepositoriesData_viewer_repositories_nodes _repository =
                _repositories[index];
            return ListTile(
              leading: ClipRRect(
                borderRadius: BorderRadius.circular(30),
                child: Image.network(_repository.owner.avatarUrl.value),
              ),
              title: Text(_repository.name),
              subtitle: Text(
                _repository.description ?? 'No description',
                maxLines: 2,
              ),
              onTap: () async {
                final url = _repository.url.value;
                if (await canLaunch(url)) await launch(url);
              },
            );
          },
        );
      },
    );
  }
}
