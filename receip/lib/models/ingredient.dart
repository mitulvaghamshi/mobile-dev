part of 'explore_recipe.dart';

class Ingredients {
  const Ingredients({
    required this.imageUrl,
    required this.title,
    required this.source,
  });

  factory Ingredients.fromJson(Map<String, dynamic> json) {
    return Ingredients(
      imageUrl: json['imageUrl'] ?? '',
      title: json['title'] ?? '',
      source: json['source'] ?? '',
    );
  }

  final String imageUrl;
  final String title;
  final String source;
}
