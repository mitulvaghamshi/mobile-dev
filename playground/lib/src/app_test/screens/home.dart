// Copyright 2020 The Flutter team. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:flutter/material.dart';
import 'package:playground/src/app_test/models/favorites.dart';
import 'package:playground/src/app_test/screens/favorites.dart';
import 'package:provider/provider.dart';

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  static String routeName = '/';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Testing Sample'),
        actions: <Widget>[
          TextButton.icon(
            label: const Text('Favorites'),
            icon: const Icon(Icons.favorite_border),
            style: TextButton.styleFrom(primary: Colors.white),
            onPressed: () => Navigator.of(context).pushNamed(
              FavoritesPage.routeName,
            ),
          ),
        ],
      ),
      body: ListView.builder(
        itemCount: 100,
        cacheExtent: 20.0,
        padding: const EdgeInsets.symmetric(vertical: 16),
        itemBuilder: (context, index) => _ItemTile(index),
      ),
    );
  }
}

class _ItemTile extends StatelessWidget {
  const _ItemTile(this.itemNo);

  final int itemNo;

  @override
  Widget build(BuildContext context) {
    var _favoritesList = Provider.of<Favorites>(context);
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: ListTile(
        leading: CircleAvatar(
          backgroundColor: Colors.primaries[itemNo % Colors.primaries.length],
        ),
        title: Text('Item $itemNo', key: Key('text_$itemNo')),
        trailing: IconButton(
          key: Key('icon_$itemNo'),
          icon: _favoritesList.items.contains(itemNo)
              ? const Icon(Icons.favorite, color: Colors.red)
              : const Icon(Icons.favorite_border),
          onPressed: () {
            !_favoritesList.items.contains(itemNo)
                ? _favoritesList.add(itemNo)
                : _favoritesList.remove(itemNo);
            ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(
                content: Text(_favoritesList.items.contains(itemNo)
                    ? 'Added to favorites.'
                    : 'Removed from favorites.'),
                duration: const Duration(seconds: 1),
              ),
            );
          },
        ),
      ),
    );
  }
}
