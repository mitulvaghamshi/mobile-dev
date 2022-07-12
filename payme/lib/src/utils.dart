import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

void showBanner(BuildContext context, String message) {
  void dismiss() => ScaffoldMessenger.of(context).clearMaterialBanners();
  ScaffoldMessenger.of(context).showMaterialBanner(
    MaterialBanner(content: Text(message), actions: [
      TextButton(onPressed: dismiss, child: const Text('Dismiss')),
    ]),
  );
  Future.delayed(const Duration(seconds: 5), dismiss);
}

String getFormatedDateTime(DateTime dateTime) =>
    DateFormat('MMM dd, yyyy hh:mm a').format(dateTime);
