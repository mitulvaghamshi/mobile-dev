import 'package:gql_exec/gql_exec.dart';

class QueryException implements Exception {
  const QueryException(this.errors);
  final List<GraphQLError> errors;
  @override
  String toString() {
    final String _errors = errors.map((GraphQLError e) => '$e').join(',');
    return 'Query Exception: $_errors';
  }
}
