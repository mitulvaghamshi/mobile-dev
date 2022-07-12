import 'package:cupertino_store/model/model.dart';
import 'package:cupertino_store/views/src/product_row_item.dart';
import 'package:cupertino_store/utils/src/constants.dart';
import 'package:flutter/cupertino.dart';
import 'package:provider/provider.dart';

class ProductListTab extends StatelessWidget {
  const ProductListTab({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Consumer<AppStateModel>(
      builder: (BuildContext context, AppStateModel value, Widget? child) {
        final List<Product> _products = value.getProducts();
        return CustomScrollView(
          semanticChildCount: _products.length,
          slivers: <Widget>[
            const CupertinoSliverNavigationBar(
              largeTitle: Text(appTitle),
            ),
            SliverSafeArea(
              sliver: SliverList(
                delegate: SliverChildBuilderDelegate((_, int index) {
                  if (index < _products.length) {
                    return ProductRowItem(
                      product: _products[index],
                      lastItem: index == _products.length - 1,
                    );
                  }
                  return null;
                }),
              ),
            ),
          ],
        );
      },
    );
  }
}
