package novel.internal.factories;

import novel.api.Novel;
import novel.api.streams.NovelPaperStream;
import novel.api.streams.NovelPenStream;
import novel.api.types.adapt.Novelable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StreamFactoryTest {

    @Test
    void simpleIOTest() throws IOException {
        var novel = Novel.newBuilder().build();
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            var out = new NovelPenStream(baos)) {
            novel.write(out, new Dummy());
            out.flush();
            try(var in = new NovelPaperStream(baos.toByteArray())) {
                Dummy dummy = novel.read(in, Dummy.class);
                Assertions.assertEquals(new Dummy(), dummy);
            }
        }
    }

    enum TestEnum {
        A,
        B,
        C
    }

    private static class Dummy implements Novelable {

        Stream<Character> a = Stream.of('a');
        Stream<TestEnum> b = Stream.of(TestEnum.A, TestEnum.B, TestEnum.C);
        Stream<Integer> c = ((Supplier<Stream<Integer>>)()->{
            Stream.Builder<Integer> builder = Stream.builder();
            builder.add(2);
            builder.add(4);
            builder.add(6);
            return builder.build();
        }).get();
        Stream<String> d = Stream.of("d");

        Stream<String> e = ((Supplier<Stream<String>>)()->{
            Stream.Builder<String> builder = Stream.builder();
            builder.add("hello");
            builder.add("goodbye");
            builder.add("xD");
            return builder.build();
        }).get();

        Stream<Boolean> f = ((Supplier<Stream<Boolean>>)()->{
            Stream.Builder<Boolean> builder = Stream.builder();
            builder.add(false);
            builder.add(true);
            return builder.build();
        }).get();

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Dummy)) return false;
            Dummy dummy = (Dummy) o;
            //this will consume the streams, but that's ok for this test
            return iteratingEquals(a, dummy.a)
                    && iteratingEquals(b, dummy.b)
                    && iteratingEquals(c, dummy.c)
                    && iteratingEquals(d, dummy.d)
                    && iteratingEquals(e, dummy.e)
                    && iteratingEquals(f, dummy.f)
                    ;
        }

    }

    private static boolean iteratingEquals(Stream<?> a, Stream<?> b) {
        Iterator<?> ita = a.iterator();
        Iterator<?> itb = b.iterator();
        while(ita.hasNext() || itb.hasNext()) {
            if(!ita.next().equals(itb.next())) {
                return false;
            }
        }
        return true;
    }


}

