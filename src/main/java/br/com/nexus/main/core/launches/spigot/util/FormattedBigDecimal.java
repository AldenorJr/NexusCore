package br.com.nexus.main.core.launches.spigot.util;

import java.math.BigDecimal;

public class FormattedBigDecimal {

    public static String[] sufixos = new String[]{"", "K", "M", "B", "T", "Q", "QQ", "S", "SS", "OC", "N", "D", "UN", "DD", "TR",
            "QT", "QN", "SD", "SPD", "QD", "ND", "VG", "UVG"};

    public static String formatarBigInteger(BigDecimal valor) {
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

    public static BigDecimal desformatarBigInteger(String valorFormat) {
        valorFormat = valorFormat.toUpperCase();
        for (int i = sufixos.length - 1; i >= 0; i--) {
            if (valorFormat.endsWith(sufixos[i])) {
                BigDecimal number = new BigDecimal(valorFormat.replace(sufixos[i], ""));
                return number.multiply(BigDecimal.valueOf(1000.0).pow(i));
            }
        }
        throw new IllegalArgumentException("Sufixo desconhecido em: " + valorFormat);
    }

    public static boolean verificarSufixo(String valorFormat) {
        for (String suffix : sufixos) {
            if (valorFormat.toLowerCase().endsWith(suffix.toLowerCase())) {
                String possibleNumber = valorFormat.substring(0, valorFormat.length() - suffix.length());
                try {
                    new BigDecimal(possibleNumber);
                    return true;
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return false;
    }





}
