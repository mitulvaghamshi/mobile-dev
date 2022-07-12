import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';

final paymentsRef = FirebaseFirestore.instance
    .collection('paymedb')
    .doc(FirebaseAuth.instance.currentUser!.email)
    .collection('payments')
    .withConverter<Payment>(
      fromFirestore: (snapshot, options) =>
          Payment.fromJson(snapshot.reference, snapshot.data()!),
      toFirestore: (payment, options) => payment.toJson(),
    );

class Payment {
  const Payment({
    required this.datetime,
    required this.hours,
    required this.note,
    this.reference,
  });

  Payment.fromJson(ref, json)
      : this(
          datetime: json['datetime'] as Timestamp,
          hours: json['hours'] as num,
          note: json['note'] as String,
          reference: ref,
        );

  final Timestamp datetime;
  final num hours;
  final String note;
  final DocumentReference<Map<String, Object?>>? reference;

  Map<String, Object?> toJson() =>
      {'datetime': datetime, 'hours': hours, 'note': note};
}
