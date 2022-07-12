import 'package:flutter/material.dart';

import 'models.dart';

class ProfileManager extends ChangeNotifier {
  User get getUser {
    return User(
      firstName: 'Stef',
      lastName: 'Patt',
      role: 'Flutterista',
      profileImageUrl: 'assets/profile_pics/person_stef.jpeg',
      points: 100,
      darkMode: _darkMode,
    );
  }

  bool _didSelectUser = false;
  bool _tapOnRaywenderlich = false;
  bool _darkMode = false;

  bool get didSelectUser => _didSelectUser;
  bool get didTapOnRaywenderlich => _tapOnRaywenderlich;
  bool get darkMode => _darkMode;

  set darkMode(bool darkMode) {
    _darkMode = darkMode;
    notifyListeners();
  }

  void tapOnRaywenderlich(bool selected) {
    _tapOnRaywenderlich = selected;
    notifyListeners();
  }

  void tapOnProfile(bool selected) {
    _didSelectUser = selected;
    notifyListeners();
  }
}
