package de.bplaced.mopfsoft.message;

import java.io.File;
import java.util.Map;

import de.bplaced.mopfsoft.network.ClientFileTransferThread;

public class GiveFileTransferInfo extends Message implements ExecutableClient  {

	public GiveFileTransferInfo(Map<String, String> args) {
		super(args);
	}

	@Override
	public void execute() {
		ClientFileTransferThread.getInstance().prepareForNewFileTransfer(new File(args.get("Path")), Long.parseLong(args.get("Length")));
	}

}
