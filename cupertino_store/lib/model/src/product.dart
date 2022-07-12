import 'package:cupertino_store/model/src/category.dart';

class Product {
  const Product({
    required this.id,
    required this.price,
    required this.name,
    required this.isFeatured,
    required this.category,
  });

  final int id;
  final int price;
  final String name;
  final bool isFeatured;
  final Category category;

  String get assetName => '$id-0.jpg';
  String get assetPackage => 'shrine_images';

  @override
  String toString() => '''
$name {
  id=$id,
  price=$price,
  category=$category,
  isFeatured=$isFeatured
}''';
}
