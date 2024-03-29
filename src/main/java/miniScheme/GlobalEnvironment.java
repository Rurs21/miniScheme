package miniScheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;


public class GlobalEnvironment {

  public static Map<String, Object> build() {
    var env = new HashMap<String, Object>();

    env.put("begin", (Procedure) (List<Object> params) -> {
      return params.get(params.size() - 1);
    });

    env.put("*", (Procedure) (List<Object> params) -> {
      var product = (Double) params.get(0);
      for (Object x : params.subList(1, params.size())) product *= (Double) x;
      return product;
    });

    env.put("<", (Procedure) (List<Object> params) -> {
      var result = true;
      for (int i = 0; i < params.size()-1; i++) result = result && (Double) params.get(i) < (Double) params.get(i+1);
      return result;
    });

    env.put("+", (Procedure) (List<Object> params) -> {
      var result = (Double) params.get(0);
      for (Object x : params.subList(1, params.size())) result += (Double) x;
      return result;
    });

    env.put("-", (Procedure) (List<Object> params) -> {
      var result = (Double) params.get(0);
      for (Object x : params.subList(1, params.size())) result -= (Double) x;
      return result;
    });

    env.put("append", (Procedure) (List<Object> params) -> {
      var concat = new ArrayList<Object>();
      for (int i = 0; i < params.size(); i++) concat.addAll((List<Object>) params.get(i));
      return concat;
    });

    env.put("filter", (Procedure) (List<Object> params) -> {
      var fn = (Procedure) params.get(0);
      var xs = (List<Object>) params.get(1);
      return xs.stream().filter(x -> (boolean) fn.call(List.of(x))).collect(toList());
    });

    env.put("count", (Procedure) (List<Object> params) -> {
      return (double) ((List<Object>) params.get(0)).size();
    });

    env.put("head", (Procedure) (List<Object> params) -> {
      return ((List<Object>) params.get(0)).get(0);
    });

    env.put("tail", (Procedure) (List<Object> params) -> {
      var list = (List<Object>) params.get(0);
      return list.subList(1,list.size());
    });

    //TODO String support
    env.put("eq", (Procedure) (List<Object> params) ->
      params.stream().map(el -> {
        if (el instanceof Integer) return (double) el;
        else return el;
      }).distinct().toArray().length <= 1);

    env.put("and", (Procedure) (List<Object> params) -> ! params.contains(false));

    env.put("not", (Procedure) (List<Object> params) -> ! (boolean) params.get(0));

    return env;
  }
}
