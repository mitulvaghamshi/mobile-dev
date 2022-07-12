import 'package:cupertino_store/model/model.dart';
import 'package:cupertino_store/utils/utils.dart';
import 'package:cupertino_store/views/src/shopping_cart_item.dart';
import 'package:flutter/cupertino.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

const double _kDateTimePickerHeight = 216;

class ShoppingCartTab extends StatefulWidget {
  const ShoppingCartTab({Key? key}) : super(key: key);

  @override
  _ShoppingCartTabState createState() => _ShoppingCartTabState();
}

class _ShoppingCartTabState extends State<ShoppingCartTab> {
  String? name;
  String? email;
  String? location;
  String? pin;
  DateTime dateTime = DateTime.now();
  final NumberFormat _currencyFormat = NumberFormat.currency(symbol: r'$');

  Widget _buildDateTimePicker() {
    return Column(children: <Widget>[
      Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: <Widget>[
        Row(children: const <Widget>[
          Icon(
            CupertinoIcons.clock,
            color: CupertinoColors.lightBackgroundGray,
            size: 28,
          ),
          SizedBox(width: 6),
          Text(
            'Delivery time',
            style: Styles.deliveryTimeLabel,
          ),
        ]),
        Text(
          DateFormat.yMMMd().add_jm().format(dateTime),
          style: Styles.deliveryTime,
        ),
      ]),
      SizedBox(
        height: _kDateTimePickerHeight,
        child: CupertinoDatePicker(
          initialDateTime: dateTime,
          onDateTimeChanged: (DateTime newDateTime) {
            setState(() => dateTime = newDateTime);
          },
        ),
      ),
    ]);
  }

  Widget _buildNameField() {
    return CupertinoTextField(
      prefix: const Icon(
        CupertinoIcons.person_solid,
        color: CupertinoColors.lightBackgroundGray,
        size: 28,
      ),
      padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 12),
      clearButtonMode: OverlayVisibilityMode.editing,
      textCapitalization: TextCapitalization.words,
      autocorrect: false,
      placeholder: 'Name',
      onChanged: (String newName) {
        setState(() => name = newName);
      },
      decoration: Styles.boxDecoration,
    );
  }

  Widget _buildEmailField() {
    return const CupertinoTextField(
      prefix: Icon(
        CupertinoIcons.mail_solid,
        color: CupertinoColors.lightBackgroundGray,
        size: 28,
      ),
      padding: EdgeInsets.symmetric(horizontal: 6, vertical: 12),
      clearButtonMode: OverlayVisibilityMode.editing,
      keyboardType: TextInputType.emailAddress,
      autocorrect: false,
      placeholder: 'Email',
      decoration: Styles.boxDecoration,
    );
  }

  Widget _buildLocationField() {
    return const CupertinoTextField(
      prefix: Icon(
        CupertinoIcons.location_solid,
        color: CupertinoColors.lightBackgroundGray,
        size: 28,
      ),
      padding: EdgeInsets.symmetric(horizontal: 6, vertical: 12),
      clearButtonMode: OverlayVisibilityMode.editing,
      textCapitalization: TextCapitalization.words,
      placeholder: 'Location',
      decoration: Styles.boxDecoration,
    );
  }

  Widget _buildClearAllButton(AppStateModel model) {
    return CupertinoButton.filled(
      onPressed: () async {
        if (model.productsInCart.isEmpty) return;
        await showCupertinoDialog<void>(
          context: context,
          builder: (BuildContext context) {
            return CupertinoAlertDialog(
              title: const Text('Are you sure want to clear?'),
              actions: <CupertinoDialogAction>[
                CupertinoDialogAction(
                  isDestructiveAction: true,
                  onPressed: () {
                    model.clearCart();
                    Navigator.pop(context);
                  },
                  child: const Text('Yes'),
                ),
                CupertinoDialogAction(
                  onPressed: () => Navigator.pop(context),
                  isDefaultAction: true,
                  child: const Text('No'),
                ),
              ],
            );
          },
        );
      },
      child: const Text('Clear Cart'),
    );
  }

  SliverChildBuilderDelegate _buildSliverChildBuilderDelegate(
      AppStateModel model) {
    return SliverChildBuilderDelegate((_, int index) {
      final int _productIndex = index - 5;
      switch (index) {
        case 0:
          return Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: _buildNameField(),
          );
        case 1:
          return Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: _buildEmailField(),
          );
        case 2:
          return Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: _buildLocationField(),
          );
        case 3:
          return Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
            child: _buildDateTimePicker(),
          );
        case 4:
          return Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: _buildClearAllButton(model),
          );
        default:
          if (model.productsInCart.length > _productIndex) {
            return ShoppingCartItem(
              index: index,
              product: model.getProductById(
                  model.productsInCart.keys.toList()[_productIndex]),
              quantity: model.productsInCart.values.toList()[_productIndex],
              lastItem: _productIndex == model.productsInCart.length - 1,
              formatter: _currencyFormat,
            );
          } else if (model.productsInCart.keys.length == _productIndex &&
              model.productsInCart.isNotEmpty) {
            return Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: <Widget>[
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.end,
                    children: <Widget>[
                      Text(
                        'Shipping '
                        '${_currencyFormat.format(model.shippingCost)}',
                        style: Styles.productRowItemPrice,
                      ),
                      const SizedBox(height: 6),
                      Text(
                        'Tax ${_currencyFormat.format(model.tax)}',
                        style: Styles.productRowItemPrice,
                      ),
                      const SizedBox(height: 6),
                      Text(
                        'Total ${_currencyFormat.format(model.totalCost)}',
                        style: Styles.productRowTotal,
                      ),
                    ],
                  )
                ],
              ),
            );
          }
      }
      return null;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<AppStateModel>(
      builder: (BuildContext context, AppStateModel value, Widget? child) {
        return CustomScrollView(
          slivers: <Widget>[
            const CupertinoSliverNavigationBar(
              largeTitle: Text(tabCart),
            ),
            SliverSafeArea(
              sliver: SliverList(
                delegate: _buildSliverChildBuilderDelegate(value),
              ),
            )
          ],
        );
      },
    );
  }
}
