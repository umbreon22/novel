package novel.internal.factories;

import novel.internal.testutil.TestWithStreams;
import novel.api.Novel;
import novel.api.streams.NovelPaperStream;
import novel.api.streams.NovelPenStream;
import novel.api.types.adapt.Novelable;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.token.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Objects;

class ArrayFactoryTest extends TestWithStreams<Object> {

    private final Novel novel = Novel.newBuilder().build();
    private final ArrayAdapterFactory factory = new ArrayAdapterFactory();

    @Test
    void testInt() throws IOException {
        testArray(Integer[].class, new Integer[]{1,2,3});
        testArray(int[].class, new int[]{1,2,3});

        int[] uwu = new int[]{1,2,3};
        byte[] bytes = withOutputStream(stream->novel.contents().writer(int[].class).write(stream, uwu));
        try(NovelPaperStream stream = new NovelPaperStream(new ByteArrayInputStream(bytes))) {
            int[] owo = novel.contents().reader(uwu.getClass()).read(stream);
            Assertions.assertArrayEquals(uwu, owo);
        }
        try(NovelPaperStream stream = new NovelPaperStream(new ByteArrayInputStream(bytes))) {
            Integer[] owo = novel.contents().reader(Integer[].class).read(stream);
            for(int i = 0; i < owo.length; i++) {
                Assertions.assertEquals(owo[i], uwu[i]);
            }
        }
    }

    private static class WeirdClass implements Novelable {
        String a;
        int b;
        WeirdClass(String a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WeirdClass that)) return false;
            return b == that.b &&
                    Objects.equals(a, that.a);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    @Test
    void testWeirdClass() throws IOException {
        WeirdClass[] weirdClassArray = new WeirdClass[]{new WeirdClass("1", 1)};
        byte[] bytes;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream(); NovelPenStream oos = new NovelPenStream(baos)) {
            novel.write(oos, weirdClassArray);
            oos.flush();
            bytes = baos.toByteArray();
        }
        try(NovelPaperStream stream = new NovelPaperStream(new ByteArrayInputStream(bytes))) {
            WeirdClass[] owo = novel.contents().reader(weirdClassArray.getClass()).read(stream);
            Assertions.assertArrayEquals(weirdClassArray, owo);
        }
    }


    @Test
    void testString() throws IOException {
        testArray(String[].class, new String[]{"1","2","3"});
        testArray(CharSequence[].class, new String[]{"1","2","3"});
    }

    <AT> void testArray(Class<AT> clazz, AT data) throws IOException {
        ObjectDataAdapter<AT> pleaseWork = factory.create(novel, TypeToken.get(clazz));
        byte[] bytes = withOutputStream(stream->pleaseWork.write(stream, data));
        @SuppressWarnings("unchecked")
        AT paperData = (AT) withInputStream(bytes, pleaseWork::read);
        for(int i = 0; i < 3; i++){
            Assertions.assertEquals(Array.get(data, i), Array.get(paperData, i));
        }
    }
}
