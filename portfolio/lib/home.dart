import 'package:flutter/material.dart';
import 'package:portfolio/cards/about_card.dart';
import 'package:portfolio/cards/edu_card.dart';
import 'package:portfolio/cards/intro_card.dart';
import 'package:portfolio/cards/repos_card.dart';
import 'package:portfolio/cards/skills_card.dart';
import 'package:portfolio/cards/wes_card.dart';

class Home extends StatelessWidget {
  const Home({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(8),
        child: Column(children: const [
          IntroCard(),
          SkillsCard(),
          ReposCard(),
          EduCard(),
          WesCard(),
          AboutCard(),
        ]),
      ),
    );
  }
}
