package uemura.java_spring_boot_demo.component;

import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import uemura.java_spring_boot_demo.domais.transfer.IrExceltDto;

import java.io.File;
import java.util.List;

public class ReadExcel {

    public static List<IrExceltDto> read(String filePath) {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().build();
        return Poiji.fromExcel(new File(filePath), IrExceltDto.class, options);
    }
}

