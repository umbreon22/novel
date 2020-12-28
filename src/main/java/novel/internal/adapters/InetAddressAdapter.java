package novel.internal.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.write.pens.DataPen;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.adapt.ObjectDataAdapter;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressAdapter implements ObjectDataAdapter<InetAddress> {

    InetAddressAdapter(){}

    @Override
    public InetAddress read(DataPaper paper) throws DataPaperReadException {
        try {
            short length = paper.shorts();
            return InetAddress.getByAddress(paper.bytes(length));
        } catch (UnknownHostException e) {
            throw new DataPaperReadException(e);
        }
    }

    @Override
    public void write(DataPen pen, InetAddress address) {
        byte[] bytes = address.getAddress();
        pen.shorts((short)bytes.length);
        pen.bytes(bytes);
    }
}
