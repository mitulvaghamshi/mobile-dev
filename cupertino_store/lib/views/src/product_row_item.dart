import 'package:cupertino_store/model/model.dart';
import 'package:cupertino_store/utils/utils.dart';
import 'package:flutter/cupertino.dart';
import 'package:provider/provider.dart';

class ProductRowItem extends StatelessWidget {
  const ProductRowItem({
    Key? key,
    required this.product,
    required this.lastItem,
  }) : super(key: key);

  final Product product;
  final bool lastItem;

  @override
  Widget build(BuildContext context) {
    final Widget _row = SafeArea(
      top: false,
      bottom: false,
      minimum: const EdgeInsets.only(left: 16, top: 8, bottom: 8, right: 8),
      child: Row(children: <Widget>[
        ClipRRect(
          borderRadius: BorderRadius.circular(4),
          child: Image.asset(
            product.assetName,
            package: product.assetPackage,
            fit: BoxFit.cover,
            width: 76,
            height: 76,
          ),
        ),
        Expanded(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 12),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Text(product.name, style: Styles.productRowItemName),
                Text('\$${product.price}', style: Styles.productRowItemPrice),
              ],
            ),
          ),
        ),
        CupertinoButton(
          onPressed: () {
            final AppStateModel _model =
                Provider.of<AppStateModel>(context, listen: false);
            _model.addProductToCart(product.id);
          },
          child: const Icon(CupertinoIcons.add_circled, semanticLabel: 'Add'),
        ),
      ]),
    );

    if (lastItem) return _row;

    return Column(children: <Widget>[
      _row,
      Padding(
        padding: const EdgeInsets.only(left: 100, right: 16),
        child: Container(height: 1, color: Styles.productRowDivider),
      ),
    ]);
  }
}
