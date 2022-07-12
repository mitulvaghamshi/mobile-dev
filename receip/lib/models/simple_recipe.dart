class SimpleRecipe {
  const SimpleRecipe({
    required this.id,
    required this.dishImage,
    required this.title,
    required this.duration,
    required this.source,
    required this.information,
  });

  factory SimpleRecipe.fromJson(Map<String, dynamic> json) {
    return SimpleRecipe(
      id: json['id'] as String,
      dishImage: json['dishImage'] as String,
      title: json['title'] as String,
      duration: json['duration'] as String,
      source: json['source'] as String,
      information: json['information'].cast<String>() as List<String>,
    );
  }
  
  final String id;
  final String dishImage;
  final String title;
  final String duration;
  final String source;
  final List<String> information;
}
