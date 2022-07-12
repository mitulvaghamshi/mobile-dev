import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:flutterfire_ui/firestore.dart';
import 'package:payme/src/payment/payment.dart';
import 'package:payme/src/payment/payment_detailview.dart';
import 'package:payme/src/payment/payment_item.dart';

class PaymentListView extends StatelessWidget {
  const PaymentListView({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Payments Book'), actions: [
        TextButton(
          onPressed: () async => await PaymentDetailView.show(
            context: context,
            payment: Payment(datetime: Timestamp.now(), hours: 8.0, note: ''),
          ),
          child: const Text('ADD NEW', style: TextStyle(color: Colors.white)),
        ),
      ]),
      body: Column(children: [
        Expanded(
          child: FirestoreListView<Payment>(
            query: paymentsRef.orderBy('datetime', descending: true),
            loadingBuilder: (_) =>
                const Center(child: CircularProgressIndicator()),
            errorBuilder: (_, error, stackTrace) =>
                const Text('Something went wrong'),
            itemBuilder: (_, doc) => PaymentItem(payment: doc.data()),
          ),
        ),
        SizedBox(
          height: 150,
          child: Card(
            color: Theme.of(context).primaryColor,
            shape: const RoundedRectangleBorder(
              borderRadius: BorderRadius.vertical(
                top: Radius.circular(10),
                bottom: Radius.circular(40),
              ),
            ),
            child: Column(children: const [
              Text('Total Hours:'),
              Text('Total Amount:'),
              Text('Total Owing:'),
            ]),
          ),
        ),
      ]),
    );
  }
}
