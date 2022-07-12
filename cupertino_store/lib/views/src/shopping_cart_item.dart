import 'package:cupertino_store/model/model.dart';
import 'package:cupertino_store/utils/utils.dart';
import 'package:flutter/cupertino.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

class ShoppingCartItem extends StatelessWidget {
  const ShoppingCartItem({
    Key? key,
    required this.index,
    required this.product,
    required this.lastItem,
    required this.quantity,
    required this.formatter,
  }) : super(key: key);

  final Product product;
  final int index;
  final bool lastItem;
  final int quantity;
  final NumberFormat formatter;

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      top: false,
      bottom: false,
      child: Padding(
        padding: const EdgeInsets.all(8),
        child: Row(children: <Widget>[
          CupertinoButton(
            onPressed: () {
              final AppStateModel _model =
                  Provider.of<AppStateModel>(context, listen: false);
              _model.removeItemFromCart(product.id);
            },
            child: const Icon(
              CupertinoIcons.minus_circle,
              semanticLabel: 'Remove',
            ),
          ),
          ClipRRect(
            borderRadius: BorderRadius.circular(4),
            child: Image.asset(
              product.assetName,
              package: product.assetPackage,
              fit: BoxFit.cover,
              width: 40,
              height: 40,
            ),
          ),
          Expanded(
            child: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 12),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(
                        product.name,
                        style: Styles.productRowItemName,
                      ),
                      Text(
                        formatter.format(quantity * product.price),
                        style: Styles.productRowItemName,
                      ),
                    ],
                  ),
                  const SizedBox(height: 4),
                  Text(
                    '${quantity > 1 ? '$quantity x ' : ''}'
                    '${formatter.format(product.price)}',
                    style: Styles.productRowItemPrice,
                  )
                ],
              ),
            ),
          ),
        ]),
      ),
    );
  }
}
