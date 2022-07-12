import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutterfire_ui/auth.dart';
import 'package:payme/src/payment/payment_listview.dart';

class SignIn extends StatelessWidget {
  const SignIn({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return StreamBuilder<User?>(
      stream: FirebaseAuth.instance.authStateChanges(),
      builder: (context, snapshot) {
        if (snapshot.hasData) return const PaymentListView();
        return SignInScreen(
          showAuthActionSwitch: true,
          sideBuilder: (_, __) => const FlutterLogo(),
          headerBuilder: (_, __, ___) => const FlutterLogo(),
          providerConfigs: const [EmailProviderConfiguration()],
          subtitleBuilder: (context, action) {
            return Padding(
              padding: const EdgeInsets.only(bottom: 8),
              child: Text(
                action == AuthAction.signIn
                    ? 'Please sign in to continue.'
                    : 'Please create an account to continue',
              ),
            );
          },
          footerBuilder: (context, _) {
            return const Padding(
              padding: EdgeInsets.only(top: 16),
              child: Text(
                'By signing in, you agree to our terms and conditions.',
                style: TextStyle(color: Colors.grey),
              ),
            );
          },
        );
      },
    );
  }
}
