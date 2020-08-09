package novel.internal.factories;

import novel.api.Novel;
import novel.api.streams.NovelPaperStream;
import novel.api.streams.NovelPenStream;
import novel.api.types.adapt.Novelable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapFactoryTest {

    @Test
    void simpleIOTest() throws IOException {
        var novel = Novel.newBuilder().build();
        var data = new Dummy();
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            var out = new NovelPenStream(baos)) {
            novel.write(out, data);
            out.flush();
            try(var in = new NovelPaperStream(baos.toByteArray())) {
                Dummy dummy = novel.read(in, Dummy.class);
                Assertions.assertEquals(data, dummy);
            }
        }
    }
    private static class Dummy implements Novelable {

        final Map<String, String> map = new HashMap<>();

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Dummy)) return false;
            return ((Dummy)o).map.equals(map);
        }
    }

    private static boolean iteratingEquals(Collection a, Collection b) {
        if(a.size() != b.size()) {
            return false;
        }
        Iterator ita = a.iterator();
        Iterator itb = b.iterator();
        while(ita.hasNext() && itb.hasNext()) {
            if(!ita.next().equals(itb.next())) {
                return false;
            }
        }
        return true;

    }


}

