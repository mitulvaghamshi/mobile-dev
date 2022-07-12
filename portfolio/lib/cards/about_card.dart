import 'package:flutter/material.dart';
import 'package:portfolio/widget/card_widget.dart';
import 'package:portfolio/widget/link_widget.dart';
import 'package:portfolio/widget/theme_widget.dart';

class AboutCard extends StatelessWidget {
  const AboutCard({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final copyright = 'Copyright © Mitul Vaghamshi, ${DateTime.now().year}.';
    return CardWidget(
      padding: EdgeInsets.zero,
      child: ListTile(
        leading: const _AppLogo(),
        trailing: const ThemeWidget(),
        title: Text(copyright, softWrap: true),
        tileColor: Theme.of(context).colorScheme.primary,
        contentPadding: const EdgeInsets.symmetric(vertical: 8),
        onTap: () async => showAboutDialog(
          context: context,
          applicationName: 'Portfolio',
          applicationVersion: 'v1.0.0',
          applicationLegalese: copyright,
          applicationIcon: const _AppLogo(),
          children: [
            const SizedBox(height: 20),
            const Text(_aboutString, softWrap: true),
            const SizedBox(height: 30),
            const Text('Developed by: Mitul Vaghamshi'),
            const SizedBox(height: 20),
            LinkWidget(
              uri: Uri.https('mitulvaghamshi.github.io', 'privacy.html'),
              label: 'Privacy Policy.',
            ),
          ],
        ),
      ),
    );
  }
}

class _AppLogo extends StatelessWidget {
  const _AppLogo({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Image.network('img/logo.webp', width: 80);
  }
}

const _aboutString =
    'Personal portfolio app built with Flutter3/Dart. \nAll the information and links contained in this application are authentic and belongs to the developer. \nPlease refer the licenses section for fonts and dependencies used. \nAlso, take a moment to femiliarize with the privacy policy.';
