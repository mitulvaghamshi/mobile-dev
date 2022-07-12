import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:payme/src/payment/payment.dart';
import 'package:payme/src/utils.dart';

class PaymentDetailView extends StatefulWidget {
  const PaymentDetailView({
    super.key,
    required this.isNew,
    required this.payment,
  });

  final bool isNew;
  final Payment payment;

  static Future<T?> show<T>({
    required BuildContext context,
    required Payment payment,
    bool? isNew = true,
  }) async {
    return await showDialog<T>(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(isNew! ? 'Add new item' : 'Edit item'),
        content: PaymentDetailView(payment: payment, isNew: isNew),
      ),
    );
  }

  @override
  State<PaymentDetailView> createState() => _PaymentDetailViewState();
}

class _PaymentDetailViewState extends State<PaymentDetailView> {
  final TextEditingController _datetimeController = TextEditingController();
  final TextEditingController _hoursController = TextEditingController();
  final TextEditingController _noteController = TextEditingController();
  late DateTime _dateTime = widget.payment.datetime.toDate();

  @override
  void initState() {
    super.initState();
    _datetimeController.text = getFormatedDateTime(_dateTime);
    _hoursController.text = widget.payment.hours.toString();
    _noteController.text = widget.payment.note;
  }

  @override
  void dispose() {
    _datetimeController.dispose();
    _hoursController.dispose();
    _noteController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Column(mainAxisSize: MainAxisSize.min, children: [
      TextField(
        controller: _datetimeController,
        decoration: InputDecoration(
          label: const Text('Datetime'),
          hintText: 'Select date and time.',
          suffix: IconButton(
            icon: const Icon(Icons.access_time),
            onPressed: () async {
              DateTime? _date = await showDatePicker(
                context: context,
                initialDate: DateTime.now(),
                firstDate: DateTime(2010),
                lastDate: DateTime.now(),
              );
              TimeOfDay? _time = await showTimePicker(
                context: context,
                initialTime: TimeOfDay.now(),
              );
              if (_date != null && _time != null) {
                _dateTime = DateTime(_date.year, _date.month, _date.day,
                    _time.hour, _time.minute);
                _datetimeController.text = getFormatedDateTime(_dateTime);
              }
            },
          ),
        ),
      ),
      TextField(
        controller: _hoursController,
        decoration: const InputDecoration(
          label: Text('Number of hours'),
          hintText: 'Enter # of hours worked.',
        ),
      ),
      TextField(
        maxLines: 5,
        controller: _noteController,
        decoration: const InputDecoration(
          hintText: '(Optional) note/description.',
        ),
      ),
      const SizedBox(height: 10),
      if (widget.isNew)
        ElevatedButton(
          onPressed: () async {
            Navigator.pop(context, widget.isNew);
            await paymentsRef.add(_createPayment());
          },
          child: const Text('Submit'),
        )
      else
        ElevatedButton(
          onPressed: () async {
            Navigator.pop(context, widget.isNew);
            await widget.payment.reference!.update(_createPayment().toJson());
          },
          child: const Text('Save changes'),
        ),
    ]);
  }

  Payment _createPayment() {
    return Payment(
      datetime: Timestamp.fromDate(_dateTime),
      hours: num.tryParse(_hoursController.text)!,
      note: _noteController.text,
    );
  }
}
