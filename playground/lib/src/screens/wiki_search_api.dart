import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_html/flutter_html.dart';
import 'package:http/http.dart' as http;

class WikiSearchApiApp extends StatefulWidget {
  const WikiSearchApiApp({Key? key}) : super(key: key);

  static const routeName = '/wikisearchapi';

  @override
  _WikiSearchApiAppState createState() => _WikiSearchApiAppState();
}

class _WikiSearchApiAppState extends State<WikiSearchApiApp> {
  final searchTextController = TextEditingController();
  List<WikiSearchEntity> searchList = [];

  void _search() {
    String str = searchTextController.text;
    RequestService.query(str).then((WikiSearchResponse? response) {
      setState(() => searchList = response!.query.search);
    });
  }

  @override
  void dispose() {
    searchTextController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Wikipedia search API')),
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Expanded(
                      child: TextField(
                        obscureText: false,
                        controller: searchTextController,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'TextField',
                        ),
                      ),
                    ),
                    const SizedBox(width: 10),
                    SizedBox(
                      height: 60,
                      child: OutlinedButton(
                        onPressed: _search,
                        child: const Text("Search"),
                      ),
                    ),
                  ]),
              const SizedBox(height: 10),
              Expanded(
                child: SingleChildScrollView(
                  child: ListView.builder(
                    primary: false,
                    shrinkWrap: true,
                    itemCount: searchList.length,
                    itemBuilder: (_, int index) {
                      return WikiSearchItemWidget(entity: searchList[index]);
                    },
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class WikiSearchItemWidget extends StatelessWidget {
  const WikiSearchItemWidget({
    Key? key,
    required this.entity,
  }) : super(key: key);

  final WikiSearchEntity entity;

  @override
  Widget build(BuildContext context) {
    return ListTile(
      title: Text(
        entity.title,
        style: const TextStyle(
          fontSize: 20,
          fontWeight: FontWeight.w700,
        ),
      ),
      subtitle: SingleChildScrollView(
        child: Html(data: entity.snippet),
      ),
      onTap: () {},
    );
  }
}

class RequestService {
  static Future<WikiSearchResponse?> query(String search) async {
    var response = await http.get(Uri.parse(
        "https://en.wikipedia.org/w/api.php?action=query&list=search&srsearch=$search&format=json"));
    // Check if response is success
    if (response.statusCode >= 200 && response.statusCode < 300) {
      var map = json.decode(response.body);
      return WikiSearchResponse.fromJson(map);
    } else {
      if (kDebugMode) {
        print("Query failed: ${response.body} (${response.statusCode})");
      }
      return null;
    }
  }
}

class WikiSearchResponse {
  const WikiSearchResponse({required this.batchComplete, required this.query});

  factory WikiSearchResponse.fromJson(Map<String, dynamic> json) {
    return WikiSearchResponse(
      batchComplete: json["batchcomplete"],
      query: WikiQueryResponse.fromJson(json["query"]),
    );
  }

  final String batchComplete;
  final WikiQueryResponse query;
}

class WikiQueryResponse {
  const WikiQueryResponse({required this.search});

  final List<WikiSearchEntity> search;

  factory WikiQueryResponse.fromJson(Map<String, dynamic> json) {
    List<dynamic> resultList = json['search'];
    List<WikiSearchEntity> search = resultList
        .map((value) => WikiSearchEntity.fromJson(value))
        .toList(growable: false);
    return WikiQueryResponse(search: search);
  }
}

class WikiSearchEntity {
  const WikiSearchEntity({
    required this.ns,
    required this.title,
    required this.pageId,
    required this.size,
    required this.wordCount,
    required this.snippet,
    required this.timestamp,
  });

  factory WikiSearchEntity.fromJson(Map<String, dynamic> json) {
    return WikiSearchEntity(
      ns: json["ns"],
      title: json["title"],
      pageId: json["pageid"],
      size: json["size"],
      wordCount: json["wordcount"],
      snippet: json["snippet"],
      timestamp: json["timestamp"],
    );
  }

  final int ns;
  final String title;
  final int pageId;
  final int size;
  final int wordCount;
  final String snippet;
  final String timestamp;
}
