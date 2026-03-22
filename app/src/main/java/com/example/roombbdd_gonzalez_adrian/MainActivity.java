package com.example.roombbdd_gonzalez_adrian;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.roombbdd_gonzalez_adrian.adapters.TagsCheckboxAdapter;
import com.example.roombbdd_gonzalez_adrian.adapters.TasquesAdapter;
import com.example.roombbdd_gonzalez_adrian.database.AppDatabase;
import com.example.roombbdd_gonzalez_adrian.entities.Tasques;
import com.example.roombbdd_gonzalez_adrian.entities.Tags;
import com.example.roombbdd_gonzalez_adrian.entities.TascaTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    
    private AppDatabase db;
    private EditText etTitol, etDescripcio, etNouTag;
    private Spinner spinnerEstat, spinnerFiltroTag;
    private ListView lvTags, lvTasques;
    private Button btnAfegirTasca, btnAfegirTag;
    
    private List<Tags> allTags = new ArrayList<>();
    private List<Tasques> allTasques = new ArrayList<>();
    private TagsCheckboxAdapter tagsAdapter;
    private TasquesAdapter tasquesAdapter;
    private ArrayAdapter<String> estatAdapter, filterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
            
            db = AppDatabase.getInstance(this);
            initializeViews();
            loadData();
        } catch (Throwable e) {
            e.printStackTrace();
            android.util.Log.e("MainActivity", "Error en onCreate: " + e.getMessage(), e);
            Toast.makeText(this, "Critical: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    
    private void initializeViews() {
        try {
            etTitol = findViewById(R.id.etTitol);
            etDescripcio = findViewById(R.id.etDescripcio);
            etNouTag = findViewById(R.id.etNouTag);
            spinnerEstat = findViewById(R.id.spinnerEstat);
            spinnerFiltroTag = findViewById(R.id.spinnerFiltroTag);
            lvTags = findViewById(R.id.lvTags);
            lvTasques = findViewById(R.id.lvTasques);
            btnAfegirTasca = findViewById(R.id.btnAfegirTasca);
            btnAfegirTag = findViewById(R.id.btnAfegirTag);
            
            if (etTitol == null || spinnerEstat == null || lvTasques == null) {
                throw new RuntimeException("Vistas no encontradas en el layout");
            }
            
            List<String> estats = new ArrayList<>();
            estats.add("pendent");
            estats.add("en_proces");
            estats.add("completada");
            
            estatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estats);
            estatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEstat.setAdapter(estatAdapter);
            
            btnAfegirTasca.setOnClickListener(v -> {
                try {
                    afegirTasca();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            btnAfegirTag.setOnClickListener(v -> {
                try {
                    afegirNouTag();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
            android.util.Log.e("MainActivity", "Error en initializeViews: " + e.getMessage(), e);
            throw new RuntimeException("Error inicializando vistas", e);
        }
    }
    
    private void loadData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                android.util.Log.d("MainActivity", "Iniciando loadData");
                
                // Cargar tags
                allTags = db.tagsDao().getAllTags();
                android.util.Log.d("MainActivity", "Tags cargados: " + (allTags != null ? allTags.size() : 0));
                
                if (allTags == null) allTags = new ArrayList<>();
                
                if (allTags.isEmpty()) {
                    android.util.Log.d("MainActivity", "Insertando tags iniciales");
                    db.tagsDao().insertTag(new Tags("casa"));
                    db.tagsDao().insertTag(new Tags("feina"));
                    allTags = db.tagsDao().getAllTags();
                    if (allTags == null) allTags = new ArrayList<>();
                }
                
                // Cargar tasques
                allTasques = db.tasquesDao().getAllTasques();
                if (allTasques == null) allTasques = new ArrayList<>();
                
                android.util.Log.d("MainActivity", "Datos cargados, actualizando UI");
                
                // Preparar opciones de filtro
                List<String> filterOptions = new ArrayList<>();
                filterOptions.add("Todos");
                for (Tags tag : allTags) {
                    if (tag != null && tag.nom != null) {
                        filterOptions.add(tag.nom);
                    }
                }
                
                // Actualizar en el thread principal
                runOnUiThread(() -> {
                    try {
                        tagsAdapter = new TagsCheckboxAdapter(MainActivity.this, allTags);
                        tasquesAdapter = new TasquesAdapter(MainActivity.this, new ArrayList<>(allTasques));
                        filterAdapter = new ArrayAdapter<>(MainActivity.this, 
                            android.R.layout.simple_spinner_item, filterOptions);
                        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        
                        if (lvTags != null) lvTags.setAdapter(tagsAdapter);
                        if (lvTasques != null) lvTasques.setAdapter(tasquesAdapter);
                        
                        setupSpinnerListener();
                        
                        if (spinnerFiltroTag != null) spinnerFiltroTag.setAdapter(filterAdapter);
                        
                        setupListViewListener();
                        
                        android.util.Log.d("MainActivity", "UI actualizada correctamente");
                    } catch (Throwable e) {
                        e.printStackTrace();
                        android.util.Log.e("MainActivity", "Error en runOnUiThread: " + e.getMessage(), e);
                        Toast.makeText(MainActivity.this, "Error UI: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Throwable e) {
                e.printStackTrace();
                android.util.Log.e("MainActivity", "Error en loadData: " + e.getMessage(), e);
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Error cargando datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }
    
    private void setupSpinnerListener() {
        if (spinnerFiltroTag == null) return;
        try {
            spinnerFiltroTag.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                    try {
                        filtrarTasques();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onNothingSelected(android.widget.AdapterView<?> parent) {}
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setupListViewListener() {
        if (lvTasques == null) return;
        try {
            lvTasques.setOnItemClickListener((parent, view, position, id) -> {
                try {
                    if (tasquesAdapter != null && position >= 0 && position < tasquesAdapter.getCount()) {
                        Tasques t = tasquesAdapter.getItem(position);
                        if (t != null) editarTasques(t);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void afegirTasca() {
        try {
            String titol = etTitol.getText().toString().trim();
            String descripcion = etDescripcio.getText().toString().trim();
            String estat = spinnerEstat.getSelectedItem().toString();
            
            if (titol.isEmpty()) {
                Toast.makeText(this, "Introdueix un títol", Toast.LENGTH_SHORT).show();
                return;
            }
            
            Tasques t = new Tasques(titol, descripcion, estat, System.currentTimeMillis(), System.currentTimeMillis());
            
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    long id = db.tasquesDao().insertTasques(t);
                    
                    if (tagsAdapter != null) {
                        Map<Integer, Boolean> selected = tagsAdapter.getSelectedTags();
                        for (Integer tagId : selected.keySet()) {
                            if (selected.get(tagId)) {
                                db.tascaTagDao().insertTascaTag(new TascaTag((int)id, tagId));
                            }
                        }
                    }
                    
                    runOnUiThread(() -> {
                        etTitol.setText("");
                        etDescripcio.setText("");
                        Toast.makeText(MainActivity.this, "Tasca afegida", Toast.LENGTH_SHORT).show();
                        recarregarTasques();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    private void afegirNouTag() {
        try {
            String nom = etNouTag.getText().toString().trim();
            if (nom.isEmpty()) {
                Toast.makeText(this, "Introdueix un nom", Toast.LENGTH_SHORT).show();
                return;
            }
            
            Tags t = new Tags(nom);
            
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    db.tagsDao().insertTag(t);
                    
                    runOnUiThread(() -> {
                        etNouTag.setText("");
                        Toast.makeText(MainActivity.this, "Tag afegit", Toast.LENGTH_SHORT).show();
                        recarregarTags();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void recarregarTags() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                allTags = db.tagsDao().getAllTags();
                if (allTags == null) allTags = new ArrayList<>();
                
                List<String> opts = new ArrayList<>();
                opts.add("Todos");
                for (Tags tag : allTags) {
                    opts.add(tag.nom);
                }
                
                filterAdapter = new ArrayAdapter<>(MainActivity.this, 
                    android.R.layout.simple_spinner_item, opts);
                filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                
                tagsAdapter = new TagsCheckboxAdapter(MainActivity.this, allTags);
                
                runOnUiThread(() -> {
                    lvTags.setAdapter(tagsAdapter);
                    spinnerFiltroTag.setAdapter(filterAdapter);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    private void filtrarTasques() {
        try {
            Object item = spinnerFiltroTag.getSelectedItem();
            if (item == null) return;
            
            String filtro = item.toString();
            
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    List<Tasques> resultado = new ArrayList<>();
                    
                    if ("Todos".equals(filtro)) {
                        resultado = db.tasquesDao().getAllTasques();
                    } else {
                        resultado = db.tasquesDao().getTasquesByTagName(filtro);
                    }
                    
                    if (resultado == null) resultado = new ArrayList<>();
                    
                    final List<Tasques> res = resultado;
                    runOnUiThread(() -> {
                        if (tasquesAdapter != null) {
                            tasquesAdapter.clear();
                            tasquesAdapter.addAll(res);
                            tasquesAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void recarregarTasques() {
        try {
            Object item = spinnerFiltroTag.getSelectedItem();
            String filtro = (item != null) ? item.toString() : "Todos";
            
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    List<Tasques> resultado = new ArrayList<>();
                    
                    if ("Todos".equals(filtro)) {
                        resultado = db.tasquesDao().getAllTasques();
                    } else {
                        resultado = db.tasquesDao().getTasquesByTagName(filtro);
                    }
                    
                    if (resultado == null) resultado = new ArrayList<>();
                    
                    final List<Tasques> res = resultado;
                    runOnUiThread(() -> {
                        if (tasquesAdapter != null) {
                            tasquesAdapter.clear();
                            tasquesAdapter.addAll(res);
                            tasquesAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void editarTasques(Tasques t) {
        try {
            EditText input = new EditText(this);
            input.setText(t.titol);
            
            new android.app.AlertDialog.Builder(this)
                .setTitle("Editar")
                .setView(input)
                .setPositiveButton("Guardar", (d, w) -> {
                    t.titol = input.getText().toString();
                    t.dataCanvi = System.currentTimeMillis();
                    
                    Executors.newSingleThreadExecutor().execute(() -> {
                        try {
                            db.tasquesDao().updateTasques(t);
                            runOnUiThread(() -> {
                                Toast.makeText(MainActivity.this, "Actualizado", Toast.LENGTH_SHORT).show();
                                recarregarTasques();
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                })
                .setNegativeButton("Cancelar", null)
                .setNeutralButton("Eliminar", (d, w) -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        try {
                            db.tasquesDao().deleteTasques(t);
                            runOnUiThread(() -> {
                                Toast.makeText(MainActivity.this, "Eliminada", Toast.LENGTH_SHORT).show();
                                recarregarTasques();
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                })
                .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
