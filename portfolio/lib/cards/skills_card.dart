import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:portfolio/utils/svgicon.dart';
import 'package:portfolio/widget/card_widget.dart';
import 'package:portfolio/widget/scroller_widget.dart';

class SkillsCard extends StatelessWidget {
  const SkillsCard({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ScrollerWidget(builder: (controller) {
      return Column(children: [
        _SkillGroup(items: _progLangs, controller: controller),
        _SkillGroup(items: _dbServices, controller: controller),
        _SkillGroup(items: _frameworkTools, controller: controller),
        _SkillGroup(items: _utilityIDEs, controller: controller),
      ]);
    });
  }
}

class _SkillGroup extends StatelessWidget {
  const _SkillGroup({
    Key? key,
    required this.items,
    required this.controller,
  }) : super(key: key);

  final List<SvgIcon> items;
  final ScrollController controller;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 5),
      child: SizedBox(
        height: 160,
        child: ScrollConfiguration(
          behavior: ScrollConfiguration.of(context).copyWith(
            dragDevices: {PointerDeviceKind.touch, PointerDeviceKind.mouse},
          ),
          child: ListView.builder(
            controller: controller,
            itemCount: items.length,
            scrollDirection: Axis.horizontal,
            physics: const AlwaysScrollableScrollPhysics(),
            prototypeItem: const SizedBox(width: 160, height: 160),
            itemBuilder: (_, index) => _SkillItem(item: items[index]),
          ),
        ),
      ),
    );
  }
}

class _SkillItem extends StatelessWidget {
  const _SkillItem({Key? key, required this.item}) : super(key: key);

  final SvgIcon item;

  @override
  Widget build(BuildContext context) {
    return CardWidget(
      padding: const EdgeInsets.symmetric(horizontal: 5, vertical: 16),
      child: Column(children: [
        SvgPicture.network(
          'svg/${item.icon}',
          width: 70,
          height: 70,
          color: item.color,
        ),
        const SizedBox(height: 10),
        Text(
          item.name,
          style: const TextStyle(
            fontSize: 16,
            fontFamily: 'OpenSans',
            fontWeight: FontWeight.w600,
          ),
        ),
      ]),
    );
  }
}

const _progLangs = [
  SvgIcon('Html5', 'html5.svg', Color(0xFFFF5722)),
  SvgIcon('CSS3', 'css3.svg', Color(0xFF3F51B5)),
  SvgIcon('Bootstrap', 'bootstrap.svg', Color(0xFFCB1ED4)),
  SvgIcon('JSON', 'json.svg', Color(0xFFFFEB3B)),
  SvgIcon('GraphQL', 'graphql.svg', Color(0xFFED2FE9)),
  SvgIcon('JavaScript', 'javascript.svg', Color(0xFFFFEB3B)),
  SvgIcon('PHP', 'php.svg', Color(0xFF607D8B)),
  SvgIcon('SASS', 'sass.svg', Color(0xFFED2FE9)),
  SvgIcon('jQuery', 'jquery.svg', Color(0xFF4CAF50)),
  SvgIcon('C++', 'cplusplus.svg', Color(0xFF2084FF)),
  SvgIcon('Dart', 'dart.svg', Color(0xFF2196F3)),
  SvgIcon('Java', 'java.svg', Color(0xFFFF3E31)),
  SvgIcon('CSharp', 'csharp.svg', Color(0xFF4CAF50)),
  SvgIcon('Golang', 'go.svg', Color(0xFF2196F3)),
  SvgIcon('Kotlin', 'kotlin.svg', Color(0xFF3F51B5)),
  SvgIcon('Angular', 'angular.svg', Color(0xFFA43D26)),
  SvgIcon('Python', 'python.svg', Color(0xFF075B85)),
  SvgIcon('Swift', 'swift.svg', Color(0xFFFF5722)),
  SvgIcon('Solidity', 'solidity.svg', Color(0xFF607D8B)),
  SvgIcon('Markdown', 'markdown.svg', Color(0xFF9100DF)),
];

const _dbServices = [
  SvgIcon('MySQL', 'mysql.svg', Color(0xFF2196F3)),
  SvgIcon('SQLServer', 'microsoftsqlserver.svg', Color(0xFF4CAF50)),
  SvgIcon('PhpMyAdmin', 'phpmyadmin.svg', Color(0xFFFF9800)),
  SvgIcon('SQLite', 'sqlite.svg', Color(0xFF9C27B0)),
  SvgIcon('Redis', 'redis.svg', Color(0xFFF44336)),
  SvgIcon('MongoDB', 'mongodb.svg', Color(0xFF4CAF50)),
  SvgIcon('Firebase', 'firebase.svg', Color(0xFFFFEB3B)),
  SvgIcon('PostgreSQL', 'postgresql.svg', Color(0xFF3F51B5)),
  SvgIcon('MS Access', 'microsoftaccess.svg', Color(0xFFF44336)),
];

const _frameworkTools = [
  SvgIcon('React', 'react.svg', Color(0xFF2196F3)),
  SvgIcon('Flutter', 'flutter.svg', Color(0xFF2196F3)),
  SvgIcon('Jetpack', 'jetpackcompose.svg', Color(0xFF4157FF)),
  SvgIcon('.NETCore', 'dotnet.svg', Color(0xFF3F51B5)),
  SvgIcon('Git', 'git.svg', Color(0xFFFF5722)),
  SvgIcon('Curl', 'curl.svg', Color(0xFF607D8B)),
  SvgIcon('CMake', 'cmake.svg', Color(0xFF3F51B5)),
  SvgIcon('Swagger', 'swagger.svg', Color(0xFF4CAF50)),
  SvgIcon('Actions', 'githubactions.svg', Color(0xFF3F51B5)),
  SvgIcon('Jenkins', 'jenkinsx.svg', Color(0xFF607D8B)),
  SvgIcon('WebAssembly', 'webassembly.svg', Color(0xFF3F51B5)),
  SvgIcon('Jest', 'jest.svg', Color(0xFF9C27B0)),
  SvgIcon('Docker', 'docker.svg', Color(0xFF2196F3)),
  SvgIcon('NodeJS', 'nodedotjs.svg', Color(0xFF4CAF50)),
  SvgIcon('MS Azure', 'microsoftazure.svg', Color(0xFF2196F3)),
  SvgIcon('Kubernetes', 'kubernetes.svg', Color(0xFF607D8B)),
  SvgIcon('Low Energy', 'bluetooth.svg', Color(0xFF2196F3)),
  SvgIcon('MUI', 'mui.svg', Color(0xFF2196F3)),
  SvgIcon('Figma', 'figma.svg', Color(0xFF3F51B5)),
  SvgIcon('MaterialDesign', 'materialdesign.svg', Color(0xFF3F51B5)),
];

const _utilityIDEs = [
  SvgIcon('Vim', 'vim.svg', Color(0xFF4CAF50)),
  SvgIcon('XCode', 'xcode.svg', Color(0xFF2196F3)),
  SvgIcon('VSCode', 'visualstudiocode.svg', Color(0xFF2196F3)),
  SvgIcon('Visual Studio', 'visualstudio.svg', Color(0xFF8600D3)),
  SvgIcon('IntelliJ Idea', 'intellijidea.svg', Color(0xFFFFEB3B)),
  SvgIcon('Android Studio', 'androidstudio.svg', Color(0xFF4CAF50)),
  SvgIcon('MS Office', 'microsoftoffice.svg', Color(0xFFF44336)),
  SvgIcon('Xampp', 'xampp.svg', Color(0xFFFF9800)),
  SvgIcon('VMWare', 'vmware.svg', Color(0xFF3F51B5)),
  SvgIcon('VirtualBox', 'virtualbox.svg', Color(0xFF2196F3)),
];
