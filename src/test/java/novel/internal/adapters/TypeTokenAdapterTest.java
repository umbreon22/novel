package novel.internal.adapters;

import novel.api.types.token.TypeToken;
import novel.internal.testutil.SimpleGoodBadTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class TypeTokenAdapterTest extends SimpleGoodBadTest<TypeToken> {

	public TypeTokenAdapterTest() {
		super(new TypeTokenAdapter(), TypeToken.get(TypeTokenAdapterTest.class), pen -> pen.strings("oops"));
	}

	@Test
	@Disabled("TODO Investigate Hashcode")
	void test() throws IOException {
		Map<Map<String, Map<String, Integer>>, Integer> object = new HashMap<>();
		Map<String, Map<String, Integer>> meme = new HashMap<>();
		object.put(meme, 1);
		meme.put("test", new HashMap<>());
		meme.get("test").put("meme", 69);
		var complexToken = TypeToken.getParameterized(object.getClass(), object.getClass().getTypeParameters());
		this.testGoodWriteRead(complexToken, this.adapter);
	}

	@Test
	void test2() throws IOException {
		Map<Map<String, Map<String, Integer>>, Integer> object = new HashMap<>();
		Map<String, Map<String, Integer>> meme = new HashMap<>();
		object.put(meme, 1);
		meme.put("test", new HashMap<>());
		meme.get("test").put("meme", 69);
		var complexToken = TypeToken.get(object.getClass());
		this.testGoodWriteRead(complexToken, this.adapter);
	}

}
