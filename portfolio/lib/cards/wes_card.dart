import 'package:flutter/material.dart';
import 'package:portfolio/widget/card_widget.dart';
import 'package:portfolio/widget/link_widget.dart';

class WesCard extends StatelessWidget {
  const WesCard({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return CardWidget(
      child: LayoutBuilder(builder: (context, constraints) {
        if (constraints.biggest.width < 740) {
          return Column(children: const [
            _WesLogo(),
            SizedBox(height: 20),
            _BadgeInfo(),
          ]);
        } else {
          return Row(children: const [
            _WesLogo(),
            SizedBox(width: 20),
            Expanded(child: _BadgeInfo()),
          ]);
        }
      }),
    );
  }
}

class _WesLogo extends StatelessWidget {
  const _WesLogo({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Image.network(
      'img/wes.webp',
      width: 200,
      height: 200,
      frameBuilder: (_, child, frame, __) => AnimatedOpacity(
        duration: const Duration(seconds: 1),
        opacity: frame == null ? 0 : 1,
        child: child,
      ),
    );
  }
}

class _BadgeInfo extends StatelessWidget {
  const _BadgeInfo({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const Text(
          'Verified International Academic Qualifications',
          style: TextStyle(fontSize: 24),
        ),
        const SizedBox(height: 10),
        const Text(
          _badgeInfoString,
          textAlign: TextAlign.justify,
          style: TextStyle(
            fontSize: 16,
            fontFamily: 'OpenSans',
          ),
        ),
        const SizedBox(height: 20),
        Wrap(spacing: 20, runSpacing: 20, children: [
          LinkWidget(
            label: 'See badge @(credly.com)',
            uri: Uri.parse(
                'https://www.credly.com/badges/b78c680a-1a8b-49df-a97d-cf08ef481db9'),
          ),
          LinkWidget(
            label: 'See report @(wes.org)',
            uri: Uri.parse(
                'https://badges.wes.org/Evidence?i=3794f10a-19b3-47f0-82bd-393315bc6584&type=ca'),
          ),
        ]),
      ],
    );
  }
}

const _badgeInfoString =
    'This badge indicates that World Education Services (WES) has evaluated the associated credential on behalf of the holder. Verifies the authenticity of the claimed credential. Provides assurance that the awarding institution and/or program was accredited at the point that the credential was issued Indicates that the associated credential has been assessed for its Canadian equivalency.';
