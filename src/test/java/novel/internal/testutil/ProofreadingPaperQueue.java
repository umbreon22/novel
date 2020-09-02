package novel.internal.testutil;

import novel.api.types.read.ProofreadingPaper;

import java.util.Queue;

public class ProofreadingPaperQueue extends DataPaperQueue implements ProofreadingPaper {

    public ProofreadingPaperQueue(Queue<Object> queue) {
        super(queue);
    }

}
