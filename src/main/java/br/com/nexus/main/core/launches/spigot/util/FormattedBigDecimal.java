package br.com.nexus.main.core.launches.spigot.util;

import java.math.BigDecimal;

public class FormattedBigDecimal {

    public static String formatarBigInteger(BigDecimal valor) {
        String[] sufixos = new String[]{"", "K", "M", "B", "T", "Q", "QQ", "S", "SS", "OC", "N", "D", "UN", "DD", "TR",
                "QT", "QN", "SD", "SPD", "QD", "ND", "VG", "UVG"};

        int indice = 0;
        BigDecimal numeroFormatado = valor;
        while (numeroFormatado.compareTo(BigDecimal.valueOf(1000.0)) >= 0 && indice < sufixos.length - 1) {
            numeroFormatado = numeroFormatado.divide(BigDecimal.valueOf(1000.0));
            indice++;
        }

        String suffix = sufixos[indice];
        if (indice == 0) {
            return numeroFormatado.toPlainString().replaceAll(",", ".") + suffix;
        } else {
            return String.format("%.2f%s", numeroFormatado, suffix).replaceAll(",", ".");
        }
    }

}
