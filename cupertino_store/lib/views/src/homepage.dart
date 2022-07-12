import 'package:cupertino_store/tabs/tabs.dart';
import 'package:cupertino_store/utils/utils.dart';
import 'package:flutter/cupertino.dart';

class CupertinoStoreHomePage extends StatelessWidget {
  const CupertinoStoreHomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return CupertinoTabScaffold(
      tabBar: CupertinoTabBar(items: const <BottomNavigationBarItem>[
        BottomNavigationBarItem(
          label: tabProduct,
          icon: Icon(CupertinoIcons.home),
        ),
        BottomNavigationBarItem(
          label: tabSearch,
          icon: Icon(CupertinoIcons.search),
        ),
        BottomNavigationBarItem(
          label: tabCart,
          icon: Icon(CupertinoIcons.shopping_cart),
        ),
      ]),
      tabBuilder: (BuildContext context, int index) {
        late final CupertinoTabView _cupertinoTabView;
        switch (index) {
          case 0:
            _cupertinoTabView = CupertinoTabView(
              builder: (_) => const CupertinoPageScaffold(
                child: ProductListTab(),
              ),
            );
            break;
          case 1:
            _cupertinoTabView = CupertinoTabView(
              builder: (_) => const CupertinoPageScaffold(
                child: SearchTab(),
              ),
            );
            break;
          case 2:
            _cupertinoTabView = CupertinoTabView(
              builder: (_) => const CupertinoPageScaffold(
                child: ShoppingCartTab(),
              ),
            );
            break;
        }
        return _cupertinoTabView;
      },
    );
  }
}
