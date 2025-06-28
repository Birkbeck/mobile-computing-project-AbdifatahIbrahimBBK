package com.example.culinary.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.culinary.model.Recipe;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class RecipeDao_Impl implements RecipeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Recipe> __insertionAdapterOfRecipe;

  private final EntityDeletionOrUpdateAdapter<Recipe> __deletionAdapterOfRecipe;

  private final EntityDeletionOrUpdateAdapter<Recipe> __updateAdapterOfRecipe;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFavoriteStatus;

  public RecipeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecipe = new EntityInsertionAdapter<Recipe>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `recipes` (`id`,`title`,`ingredients`,`instructions`,`category`,`isFavorite`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Recipe entity) {
        statement.bindLong(1, entity.id);
        if (entity.title == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.title);
        }
        if (entity.ingredients == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.ingredients);
        }
        if (entity.instructions == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.instructions);
        }
        if (entity.category == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.category);
        }
        final int _tmp = entity.isFavorite ? 1 : 0;
        statement.bindLong(6, _tmp);
      }
    };
    this.__deletionAdapterOfRecipe = new EntityDeletionOrUpdateAdapter<Recipe>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `recipes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Recipe entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfRecipe = new EntityDeletionOrUpdateAdapter<Recipe>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `recipes` SET `id` = ?,`title` = ?,`ingredients` = ?,`instructions` = ?,`category` = ?,`isFavorite` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Recipe entity) {
        statement.bindLong(1, entity.id);
        if (entity.title == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.title);
        }
        if (entity.ingredients == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.ingredients);
        }
        if (entity.instructions == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.instructions);
        }
        if (entity.category == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.category);
        }
        final int _tmp = entity.isFavorite ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.id);
      }
    };
    this.__preparedStmtOfUpdateFavoriteStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE recipes SET isFavorite = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Recipe recipe) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfRecipe.insert(recipe);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Recipe recipe) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfRecipe.handle(recipe);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Recipe recipe) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfRecipe.handle(recipe);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateFavoriteStatus(final int recipeId, final boolean isFavorite) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFavoriteStatus.acquire();
    int _argIndex = 1;
    final int _tmp = isFavorite ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, recipeId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateFavoriteStatus.release(_stmt);
    }
  }

  @Override
  public List<Recipe> getAllRecipes() {
    final String _sql = "SELECT * FROM recipes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
      final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
      final List<Recipe> _result = new ArrayList<Recipe>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Recipe _item;
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpIngredients;
        if (_cursor.isNull(_cursorIndexOfIngredients)) {
          _tmpIngredients = null;
        } else {
          _tmpIngredients = _cursor.getString(_cursorIndexOfIngredients);
        }
        final String _tmpInstructions;
        if (_cursor.isNull(_cursorIndexOfInstructions)) {
          _tmpInstructions = null;
        } else {
          _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
        }
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _item = new Recipe(_tmpTitle,_tmpIngredients,_tmpInstructions,_tmpCategory);
        _item.id = _cursor.getInt(_cursorIndexOfId);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
        _item.isFavorite = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Recipe getRecipeById(final int id) {
    final String _sql = "SELECT * FROM recipes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
      final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
      final Recipe _result;
      if (_cursor.moveToFirst()) {
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpIngredients;
        if (_cursor.isNull(_cursorIndexOfIngredients)) {
          _tmpIngredients = null;
        } else {
          _tmpIngredients = _cursor.getString(_cursorIndexOfIngredients);
        }
        final String _tmpInstructions;
        if (_cursor.isNull(_cursorIndexOfInstructions)) {
          _tmpInstructions = null;
        } else {
          _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
        }
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _result = new Recipe(_tmpTitle,_tmpIngredients,_tmpInstructions,_tmpCategory);
        _result.id = _cursor.getInt(_cursorIndexOfId);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
        _result.isFavorite = _tmp != 0;
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Recipe> searchRecipes(final String query) {
    final String _sql = "SELECT * FROM recipes WHERE title LIKE ? OR ingredients LIKE ? OR instructions LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 3;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
      final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
      final List<Recipe> _result = new ArrayList<Recipe>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Recipe _item;
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpIngredients;
        if (_cursor.isNull(_cursorIndexOfIngredients)) {
          _tmpIngredients = null;
        } else {
          _tmpIngredients = _cursor.getString(_cursorIndexOfIngredients);
        }
        final String _tmpInstructions;
        if (_cursor.isNull(_cursorIndexOfInstructions)) {
          _tmpInstructions = null;
        } else {
          _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
        }
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _item = new Recipe(_tmpTitle,_tmpIngredients,_tmpInstructions,_tmpCategory);
        _item.id = _cursor.getInt(_cursorIndexOfId);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
        _item.isFavorite = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Recipe> getFavoriteRecipes() {
    final String _sql = "SELECT * FROM recipes WHERE isFavorite = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
      final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
      final List<Recipe> _result = new ArrayList<Recipe>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Recipe _item;
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpIngredients;
        if (_cursor.isNull(_cursorIndexOfIngredients)) {
          _tmpIngredients = null;
        } else {
          _tmpIngredients = _cursor.getString(_cursorIndexOfIngredients);
        }
        final String _tmpInstructions;
        if (_cursor.isNull(_cursorIndexOfInstructions)) {
          _tmpInstructions = null;
        } else {
          _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
        }
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _item = new Recipe(_tmpTitle,_tmpIngredients,_tmpInstructions,_tmpCategory);
        _item.id = _cursor.getInt(_cursorIndexOfId);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
        _item.isFavorite = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
