import 'dart:io';

import 'package:flutter/material.dart';
import 'package:fluttericon/octicons_icons.dart';
import 'package:github_app/src/github_gql/github_gql.dart';
import 'package:github_app/src/github_login_widget.dart';
import 'package:github_app/src/query_exception.dart';
import 'package:github_app/src/screens/screens.dart';
import 'package:gql_exec/gql_exec.dart';
import 'package:gql_http_link/gql_http_link.dart';
import 'package:oauth2/oauth2.dart' as oauth2;
import 'package:window_to_front/window_to_front.dart';

class GithubApp extends StatefulWidget {
  const GithubApp({Key? key}) : super(key: key);

  @override
  State<GithubApp> createState() => _GithubAppState();
}

class _GithubAppState extends State<GithubApp> {
  int _selectedIndex = 0;

  void _onSelect(value) => setState(() => _selectedIndex = value);

  @override
  Widget build(BuildContext context) {
    return GitHubLoginWidget(
      githubScopes: const ['repo', 'read:org'],
      githubClientId: const String.fromEnvironment('ClientID'),
      githubClientSecret: const String.fromEnvironment('ClientSecret'),
      builder: (_, oauth2.Client httpClient) {
        // The WindowToFront plugin is currently implemened for macOS only.
        if (Platform.isMacOS) WindowToFront.activate();
        final HttpLink _link = HttpLink(
          'https://api.github.com/graphql',
          httpClient: httpClient,
        );
        return FutureBuilder<GViewerDetailData_viewer>(
          future: _viewerDetail(_link),
          builder: (_, AsyncSnapshot<GViewerDetailData_viewer> snapshot) {
            final _appBar = AppBar(
              leading: const Icon(Octicons.mark_github),
              title: Text(
                snapshot.hasData ? snapshot.data!.name! : 'Please wait...',
              ),
            );
            final _indexedStack = IndexedStack(
              index: _selectedIndex,
              children: [
                RepositoryList(link: _link),
                AssignedIssuesList(link: _link),
                PullRequestsList(link: _link),
              ],
            );
            if (MediaQuery.of(context).orientation == Orientation.portrait) {
              return Scaffold(
                appBar: _appBar,
                body: _indexedStack,
                bottomNavigationBar: NavigationBar(
                  height: 60,
                  selectedIndex: _selectedIndex,
                  onDestinationSelected: _onSelect,
                  destinations: const [
                    NavigationDestination(
                      icon: Icon(Octicons.repo),
                      label: 'Repos',
                    ),
                    NavigationDestination(
                      icon: Icon(Octicons.issue_opened),
                      label: 'Issues',
                    ),
                    NavigationDestination(
                      icon: Icon(Octicons.git_pull_request),
                      label: 'PRs',
                    ),
                  ],
                ),
              );
            }
            return Scaffold(
              appBar: _appBar,
              body: Row(children: [
                NavigationRail(
                  selectedIndex: _selectedIndex,
                  onDestinationSelected: _onSelect,
                  labelType: NavigationRailLabelType.all,
                  destinations: const <NavigationRailDestination>[
                    NavigationRailDestination(
                      icon: Icon(Octicons.repo),
                      label: Text('Repository'),
                    ),
                    NavigationRailDestination(
                      icon: Icon(Octicons.issue_opened),
                      label: Text('Assigned Issues'),
                    ),
                    NavigationRailDestination(
                      icon: Icon(Octicons.git_pull_request),
                      label: Text('Pull Requests'),
                    ),
                  ],
                ),
                Expanded(child: _indexedStack),
              ]),
            );
          },
        );
      },
    );
  }

  Future<GViewerDetailData_viewer> _viewerDetail(HttpLink link) async {
    final GViewerDetail _request =
        GViewerDetail((GViewerDetailBuilder builder) => builder);
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
    return GViewerDetailData.fromJson(_result.data!)!.viewer;
  }
}
