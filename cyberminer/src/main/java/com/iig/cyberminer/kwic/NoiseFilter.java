package com.iig.cyberminer.kwic;


public class NoiseFilter {
    // build the ds for the noise words
    static String[] arSingle = {"a","b","c","d","e","f","g","h","i",
                         "j","k","l","m","n","o","p","q","r",
                         "s","t","u","v","w","x","y","z","$",
                         "1","2","3","4","5","6","7","8","9","0","_"," "};
    static String[] arDouble = {"an","as","at","be","by","do","he",
                         "if","in","is","it","me","my","of",
                         "on","or","to","up","we"};
    static String[] arTriple = {"all","and","any","are","but","can",
                         "did","for","get","got","has","had",
                         "her","him","his","how","our","out",
                         "now","see","the","too","was","way","who","you"};
    static String[] arQuad   = {"also","been","both","came","come",
                         "each","from","have","here","into",
                         "like","make","many","more","most",
                         "much","must","over","said","same",
                         "only","some","such","take","than",
                         "that","them","then","they","this",
                         "very","well","were","what","with","your"};
    static String[] arFive   = {"after","about","being","could",
                         "might","never","other","since",
                         "still","their","there","these",
                         "those","under","where","which","while","would"};
    static String[] arSix    = {"before","should"};
    static String[] arSeven  = {"another","because","between","himself","through"};

    // Returns true if the parameter is a noise word
    public static boolean isNoiseWord(String strWord)
    {
        boolean blnNoiseWord = false;
        switch (strWord.length()) {
            case 1:
                for (int i=0; i<arSingle.length; i++)
                {
                    if (strWord.equals(arSingle[i]))
                    {
                        return true;
                    }
                }
            break;
            case 2:
                for (int i=0; i<arDouble.length; i++)
                {
                    if (strWord.equals(arDouble[i]))
                    {
                        return true;
                    }
                }
            break;
            case 3:
                for (int i=0; i<arTriple.length; i++)
                {
                    if (strWord.equals(arTriple[i]))
                    {
                        return true;
                    }
                }
            break;
            case 4:
                for (int i=0; i<arQuad.length; i++)
                {
                    if (strWord.equals(arQuad[i]))
                    {
                        return true;
                    }
                }
            break;
            case 5:
                for (int i=0; i<arFive.length; i++)
                {
                    if (strWord.equals(arFive[i]))
                    {
                        return true;
                    }
                }
            break;
            case 6:
                for (int i=0; i<arSix.length; i++)
                {
                    if (strWord.equals(arSix[i]))
                    {
                        return true;
                    }
                }
            break;
            case 7:
                for (int i=0; i<arSeven.length; i++)
                {
                    if (strWord.equals(arSeven[i]))
                    {
                        return true;
                    }
                }
            break;
        }

        return blnNoiseWord;
    }
}
