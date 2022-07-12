import 'package:flutter/material.dart';
import 'package:portfolio/widget/card_widget.dart';
import 'package:portfolio/widget/link_widget.dart';

class EduCard extends StatelessWidget {
  const EduCard({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Wrap(
      alignment: WrapAlignment.center,
      children: const [
        _EducationItem(
          url: 'https://www.aij.ind.in',
          year: '2014 - 2017',
          major: 'Diploma in Computer Engineering (3yr)',
          address: 'Gujarat, India.',
          institute: 'Amrut Institute - Junagadh.',
        ),
        _EducationItem(
          url: 'https://www.mohawkcollege.ca',
          year: '2020 - 2021',
          major: 'Computer System Technician - Software Support (2yr)',
          address: 'Hamilton, Ontario, Canada.',
          institute: 'Mohawk College of Applied Arts & Technologies',
        ),
      ],
    );
  }
}

class _EducationItem extends StatelessWidget {
  const _EducationItem({
    Key? key,
    required this.url,
    required this.year,
    required this.major,
    required this.address,
    required this.institute,
  }) : super(key: key);

  final String url;
  final String year;
  final String major;
  final String address;
  final String institute;

  @override
  Widget build(BuildContext context) {
    return CardWidget(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          LinkWidget(label: year, uri: Uri.parse(url)),
          const SizedBox(height: 16),
          Text(
            major,
            style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 10),
          Text(
            institute,
            style: TextStyle(
              color: Theme.of(context).focusColor.withOpacity(0.7),
            ),
          ),
          const SizedBox(height: 10),
          Text(address),
        ],
      ),
    );
  }
}
