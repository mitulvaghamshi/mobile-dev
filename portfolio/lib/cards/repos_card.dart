import 'dart:convert';

import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:http/http.dart' as http;
import 'package:portfolio/widget/card_widget.dart';
import 'package:portfolio/widget/link_widget.dart';
import 'package:portfolio/widget/scroller_widget.dart';

class ReposCard extends StatelessWidget {
  const ReposCard({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Iterable<_Repo>?>(
      future: _Repo.get(),
      builder: (context, snapshot) {
        if (!snapshot.hasData || snapshot.hasError) return const SizedBox();
        return ScrollerWidget(builder: (controller) {
          return Padding(
            padding: const EdgeInsets.symmetric(horizontal: 5),
            child: SizedBox(
              height: 210,
              child: ScrollConfiguration(
                behavior: ScrollConfiguration.of(context).copyWith(
                  dragDevices: {
                    PointerDeviceKind.touch,
                    PointerDeviceKind.mouse
                  },
                ),
                child: ListView.builder(
                  controller: controller,
                  itemCount: snapshot.data!.length,
                  scrollDirection: Axis.horizontal,
                  padding: const EdgeInsets.only(top: 5),
                  physics: const AlwaysScrollableScrollPhysics(),
                  prototypeItem: const SizedBox(width: 370, height: 210),
                  itemBuilder: (_, index) => _RepoItem(
                    repo: snapshot.data!.elementAt(index),
                  ),
                ),
              ),
            ),
          );
        });
      },
    );
  }
}

class _RepoItem extends StatelessWidget {
  const _RepoItem({Key? key, required this.repo}) : super(key: key);

  final _Repo repo;

  @override
  Widget build(BuildContext context) {
    return CardWidget(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          LinkWidget.icon(
            label: repo.name,
            icon: const _GithubIcon(),
            uri: Uri.parse(repo.htmlUrl),
          ),
          const SizedBox(height: 10),
          Text(
            repo.language,
            style: const TextStyle(
              fontSize: 18,
              fontWeight: FontWeight.bold,
            ),
          ),
          const SizedBox(height: 10),
          Text(
            repo.description,
            maxLines: 3,
            overflow: TextOverflow.ellipsis,
            style: TextStyle(
              fontFamily: 'OpenSans',
              fontWeight: FontWeight.w700,
              color: Theme.of(context).focusColor.withOpacity(0.7),
            ),
          ),
        ],
      ),
    );
  }
}

class _GithubIcon extends StatelessWidget {
  const _GithubIcon({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SvgPicture.network(
      'svg/github.svg',
      color: Theme.of(context).colorScheme.onPrimary,
      height: 24,
    );
  }
}

class _Repo {
  const _Repo({
    required this.name,
    required this.htmlUrl,
    required this.language,
    required this.description,
  });

  factory _Repo.fromJson(Map<String, dynamic> json) {
    return _Repo(
      name: json['name'],
      htmlUrl: json['html_url'],
      language: json['language'] ?? json['name'],
      description: json['description'] ?? 'No description provided...',
    );
  }

  final String name;
  final String htmlUrl;
  final String language;
  final String description;

  static Future<Iterable<_Repo>?> get() async {
    final response = await http.get(
      Uri.parse('https://api.github.com/users/mitulvaghamshi/repos'),
      headers: {'Accept': 'application/json'},
    );
    if (response.statusCode == 200) {
      final data = await json.decode(response.body);
      if (data == null) return null;
      return data.map(_Repo.fromJson);
    }
    return null;
  }
}
