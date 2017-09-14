package com.example.android.PopularMovies.Data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Table;
import net.simonvt.schematic.annotation.Unique;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by Shashvati on 9/13/2017.
 */
@Database(version = MoviesDatabase.VERSION)
public final class MoviesDatabase {
    public static final int VERSION = 1;
    @Table(MovieColumns.class) public static final String Movies = "MovieDatabase";
    public interface MovieColumns {
        @DataType(DataType.Type.INTEGER) @PrimaryKey
        @AutoIncrement
        public static final String _ID =
                "_id";
        @DataType(INTEGER) @NotNull @Unique(onConflict = ConflictResolutionType.REPLACE) String COLUMN_MOVIE_ID  = "movies_id";
        @DataType(TEXT) @NotNull String COLUMN_MOVIE_TITLE  = "movie_title";
        @DataType(TEXT) @NotNull String COLUMN_POSTER_PATH  = "poster_path";
    }
}
