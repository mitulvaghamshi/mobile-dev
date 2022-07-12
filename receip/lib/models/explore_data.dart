import 'models.dart';

class ExploreData {
  const ExploreData(this.todayRecipes, this.friendPosts);

  final List<ExploreRecipe> todayRecipes;
  final List<Post> friendPosts;
}
