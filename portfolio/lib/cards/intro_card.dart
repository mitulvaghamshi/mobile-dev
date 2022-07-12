import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:portfolio/utils/svgicon.dart';
import 'package:portfolio/widget/card_widget.dart';
import 'package:portfolio/widget/link_widget.dart';
import 'package:url_launcher/link.dart';

class IntroCard extends StatelessWidget {
  const IntroCard({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return CardWidget(child: LayoutBuilder(builder: (_, con) {
      if (con.biggest.width < 740) return const _VerticalLayout();
      return const _HorizontalLayout();
    }));
  }
}

class _VerticalLayout extends StatelessWidget {
  const _VerticalLayout({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(children: const [
      _ProfilePicture(),
      SizedBox(height: 16),
      _InfoWidget(),
      Divider(thickness: 2),
      _ButtonRow(direction: Axis.horizontal),
    ]);
  }
}

class _HorizontalLayout extends StatelessWidget {
  const _HorizontalLayout({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(children: const [
      _ProfilePicture(),
      SizedBox(width: 16),
      Expanded(child: _InfoWidget()),
      VerticalDivider(thickness: 2),
      _ButtonRow(direction: Axis.vertical),
    ]);
  }
}

class _ProfilePicture extends StatelessWidget {
  const _ProfilePicture({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 10,
      shape: const CircleBorder(),
      child: Image.network(
        'img/picture.webp',
        width: 300,
        height: 300,
        frameBuilder: (_, child, frame, __) => AnimatedOpacity(
          duration: const Duration(seconds: 2),
          opacity: frame == null ? 0 : 1,
          child: child,
        ),
      ),
    );
  }
}

class _InfoWidget extends StatelessWidget {
  const _InfoWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        const Text(
          'Software Developer',
          style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
        ),
        const SizedBox(height: 20),
        Text.rich(
          TextSpan(text: 'Mitul ', children: [
            const TextSpan(
              text: 'Vaghamshi',
              style: TextStyle(color: Colors.grey),
            ),
            TextSpan(
              text: '_',
              style: TextStyle(color: Theme.of(context).colorScheme.primary),
            ),
          ]),
          style: const TextStyle(fontSize: 50, fontWeight: FontWeight.bold),
        ),
        const SizedBox(height: 16),
        const Text(
          _introString,
          textAlign: TextAlign.justify,
          style: TextStyle(
            fontSize: 20,
            fontFamily: 'OpenSans',
            fontWeight: FontWeight.w600,
          ),
        ),
        const SizedBox(height: 20),
        LinkWidget.icon(
          label: 'Download Resume',
          icon: const Icon(Icons.download),
          uri: Uri.https('mitulvaghamshi.github.io', 'portfolio.pdf'),
        ),
      ],
    );
  }
}

class _ButtonRow extends StatelessWidget {
  const _ButtonRow({Key? key, required this.direction}) : super(key: key);

  final Axis direction;

  @override
  Widget build(BuildContext context) {
    return Wrap(
      direction: direction,
      alignment: WrapAlignment.spaceAround,
      children: _socialMedia.map((e) {
        return Link(
          uri: Uri.parse(e.name),
          target: LinkTarget.blank,
          builder: (context, followLink) {
            return IconButton(
              onPressed: followLink,
              icon: SvgPicture.network(
                'svg/${e.icon}',
                color: Theme.of(context).colorScheme.primary,
              ),
            );
          },
        );
      }).toList(),
    );
  }
}

const _introString =
    "I'm always looking for challenges in the software industry because this is the only thing that I can consider as my building blocks for learning something new. I'm always ready to learn new technologies and, its potential to develop something extraordinary.";

const _socialMedia = [
  SvgIcon('mailto:mitulvaghmashi@gmail.com', 'gmail.svg'),
  SvgIcon('https://github.com/mitulvaghamshi', 'github.svg'),
  SvgIcon('https://play.google.com/store/apps/developer?id=Mitul+Vaghamshi',
      'googleplay.svg'),
  SvgIcon('https://linkedin.com/in/mitul-vaghamshi-b7163421a', 'linkedin.svg'),
  SvgIcon('https://twitter.com/mitulvaghamshi', 'twitter.svg'),
  SvgIcon(
      'https://facebook.com/profile.php?id=100062853307399', 'facebook.svg'),
  SvgIcon('https://instagram.com/mitulvaghamshi', 'instagram.svg'),
  SvgIcon('https://api.whatsapp.com/send?phone=12899330783', 'whatsapp.svg'),
  SvgIcon('tel:+12899330783', 'phone.svg'),
];
