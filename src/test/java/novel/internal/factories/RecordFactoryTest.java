package novel.internal.factories;

import novel.api.Novel;
import novel.api.types.adapt.Novelable;
import novel.internal.testutil.DataPenQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecordFactoryTest {

	public record NovelableRecordString(String string) implements Novelable {}

	@Test
	void testWriteAndRead() {
		var novel = Novel.newDefaultInstance();
		var record = new NovelableRecordString("novel");
		var pen = new DataPenQueue();
		novel.write(pen, record);
		var read = novel.read(pen.paper(), NovelableRecordString.class);
		Assertions.assertEquals(record, read);
	}
}
