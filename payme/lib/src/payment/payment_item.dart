import 'package:flutter/material.dart';
import 'package:payme/src/payment/payment.dart';
import 'package:payme/src/payment/payment_detailview.dart';
import 'package:payme/src/utils.dart';

class PaymentItem extends StatelessWidget {
  const PaymentItem({Key? key, required this.payment}) : super(key: key);

  final Payment payment;

  @override
  Widget build(BuildContext context) {
    return Dismissible(
      key: ValueKey(payment.reference),
      dismissThresholds: const {DismissDirection.endToStart: 0.9},
      background: ElevatedButton(
        onPressed: () {},
        child: const Text('Instant swipe to left edge to delete.'),
      ),
      secondaryBackground: Container(
        padding: const EdgeInsets.all(10),
        alignment: Alignment.centerRight,
        color: Theme.of(context).errorColor,
        child: const Text('Delete', style: TextStyle(color: Colors.white)),
      ),
      confirmDismiss: (direction) async {
        if (direction == DismissDirection.endToStart) {
          bool delete = true;
          ScaffoldMessenger.of(context).showMaterialBanner(MaterialBanner(
              content: const Text('Item deleted successfully!'),
              actions: [
                TextButton(
                  onPressed: () => delete = false,
                  child: const Text('UNDO'),
                ),
              ]));
          await Future<void>.delayed(const Duration(seconds: 5), () async {
            ScaffoldMessenger.of(context).clearMaterialBanners();
            if (delete) await payment.reference!.delete();
          });
          return delete;
        }
        return null;
      },
      child: ListTile(
        leading: CircleAvatar(child: Text(payment.hours.toString())),
        title: Text(getFormatedDateTime(payment.datetime.toDate())),
        subtitle: Text(payment.note, maxLines: 2),
        onLongPress: () async {
          final bool? result = await PaymentDetailView.show(
            context: context,
            payment: payment,
            isNew: false,
          );
          if (result != null) {
            showBanner(
              context,
              'Item ${result ? 'added' : 'updated'} successfully!',
            );
          }
        },
      ),
    );
  }
}
