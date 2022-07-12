part of 'explore_recipe.dart';

class Instruction {
  const Instruction({
    required this.imageUrl,
    required this.description,
    required this.durationInMinutes,
  });

  factory Instruction.fromJson(Map<String, dynamic> json) {
    return Instruction(
      imageUrl: json['imageUrl'] ?? '',
      description: json['description'] ?? '',
      durationInMinutes: json['durationInMinutes'] ?? '',
    );
  }

  final String imageUrl;
  final String description;
  final int durationInMinutes;
}
