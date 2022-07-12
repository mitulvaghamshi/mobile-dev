import 'package:flutter/material.dart';
import 'package:url_launcher/link.dart';

class LinkWidget extends StatelessWidget {
  const LinkWidget({
    Key? key,
    required this.uri,
    required this.label,
  }) : super(key: key);

  final Uri uri;
  final String label;

  const factory LinkWidget.icon({
    Key? key,
    required Uri uri,
    required Widget icon,
    required String label,
  }) = _LinkWidgetIcon;

  @override
  Widget build(BuildContext context) {
    return Link(
      uri: uri,
      target: LinkTarget.blank,
      builder: (context, followLink) {
        return ElevatedButton(
          onPressed: followLink,
          style: _buttonStyle,
          child: Text(label),
        );
      },
    );
  }
}

class _LinkWidgetIcon extends LinkWidget {
  const _LinkWidgetIcon({
    Key? key,
    required Uri uri,
    required this.icon,
    required String label,
  }) : super(key: key, uri: uri, label: label);

  final Widget icon;

  @override
  Widget build(BuildContext context) {
    return Link(
      uri: uri,
      target: LinkTarget.blank,
      builder: (context, followLink) => ElevatedButton.icon(
        onPressed: followLink,
        icon: icon,
        style: _buttonStyle,
        label: Text(label),
      ),
    );
  }
}

final _buttonStyle = ButtonStyle(
  padding: MaterialStateProperty.all(const EdgeInsets.all(16)),
);
