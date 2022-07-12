import 'package:cupertino_store/model/model.dart';
import 'package:cupertino_store/utils/utils.dart';
import 'package:cupertino_store/views/src/product_row_item.dart';
import 'package:cupertino_store/views/src/search_bar.dart';
import 'package:flutter/cupertino.dart';
import 'package:provider/provider.dart';

class SearchTab extends StatefulWidget {
  const SearchTab({Key? key}) : super(key: key);

  @override
  _SearchTabState createState() => _SearchTabState();
}

class _SearchTabState extends State<SearchTab> {
  late final TextEditingController _controller;
  late final FocusNode _focusNode;
  String _terms = '';

  @override
  void initState() {
    super.initState();
    _controller = TextEditingController()..addListener(_onTextChanged);
    _focusNode = FocusNode();
  }

  @override
  void dispose() {
    _focusNode.dispose();
    _controller.dispose();
    super.dispose();
  }

  void _onTextChanged() => setState(() => _terms = _controller.text);

  Widget _buildSearchBox() {
    return Padding(
      padding: const EdgeInsets.all(12),
      child: SearchBar(controller: _controller, focusNode: _focusNode),
    );
  }

  @override
  Widget build(BuildContext context) {
    final AppStateModel _model =
        Provider.of<AppStateModel>(context, listen: false);
    final List<Product> _results = _model.search(_terms);
    return DecoratedBox(
      decoration: const BoxDecoration(color: Styles.scaffoldBackground),
      child: SafeArea(
        child: Column(children: <Widget>[
          _buildSearchBox(),
          Expanded(
            child: ListView.builder(
              itemCount: _results.length,
              itemBuilder: (_, int index) => ProductRowItem(
                product: _results[index],
                lastItem: index == _results.length - 1,
              ),
            ),
          ),
        ]),
      ),
    );
  }
}
