import 'package:cupertino_store/model/model.dart';
import 'package:flutter/foundation.dart' show ChangeNotifier;

const double _salesTaxRate = 0.6;
const double _shippingCostPerItem = 7;

class AppStateModel extends ChangeNotifier {
  final Map<int, int> _productsInCart = <int, int>{};
  List<Product> _availableProducts = <Product>[];
  Category _selectedCategory = Category.all;

  Category get selectedCategory => _selectedCategory;

  Map<int, int> get productsInCart => Map<int, int>.from(_productsInCart);

  double get tax => subtotalCost * _salesTaxRate;

  double get totalCost => subtotalCost + shippingCost + tax;

  int get totalCartQuantity {
    return _productsInCart.values.fold(0, (int accumulator, int value) {
      return accumulator + value;
    });
  }

  double get subtotalCost {
    return _productsInCart.keys.map((int id) {
      return getProductById(id).price * _productsInCart[id]!;
    }).fold(0, (double accumulator, int extendedPrice) {
      return accumulator + extendedPrice;
    });
  }

  double get shippingCost {
    final num _fold =
        _productsInCart.values.fold(0.0, (num accumulator, int itemCount) {
      return accumulator + itemCount;
    });
    return _shippingCostPerItem * _fold;
  }

  List<Product> getProducts() {
    if (_selectedCategory == Category.all) {
      return List<Product>.from(_availableProducts);
    } else {
      return _availableProducts.where((Product p) {
        return p.category == _selectedCategory;
      }).toList();
    }
  }

  List<Product> search(String searchTerms) {
    return getProducts().where((Product p) {
      return p.name.toLowerCase().contains(searchTerms.toLowerCase());
    }).toList();
  }

  void addProductToCart(int productId) {
    if (!_productsInCart.containsKey(productId)) {
      _productsInCart[productId] = 1;
    } else {
      _productsInCart[productId] = _productsInCart[productId]! + 1;
    }
    notifyListeners();
  }

  void removeItemFromCart(int productId) {
    if (_productsInCart.containsKey(productId)) {
      if (_productsInCart[productId] == 1) {
        _productsInCart.remove(productId);
      } else {
        _productsInCart[productId] = _productsInCart[productId]! - 1;
      }
    }
    notifyListeners();
  }

  Product getProductById(int id) {
    return _availableProducts.firstWhere((Product p) => p.id == id);
  }

  void clearCart() {
    _productsInCart.clear();
    notifyListeners();
  }

  void loadProducts() {
    _availableProducts = ProductsRepository.loadProducts(Category.all);
    notifyListeners();
  }

  void setCategory(Category newCategory) {
    _selectedCategory = newCategory;
    notifyListeners();
  }
}
