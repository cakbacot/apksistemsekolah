package tampilan;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

/**
 * Sistem desain & utilitas tema terpusat (Modern Navy & Royal Blue).
 * Kompatibel penuh dengan JDK 8 dan NetBeans 8.2 GUI Builder (Matisse).
 */
public final class UITheme {

    // Palet Utama — Modern Navy & Royal Blue
    public static final Color NAVY_DARK = new Color(15, 23, 42);        // #0F172A
    public static final Color NAVY_SURFACE = new Color(30, 41, 59);     // #1E293B
    public static final Color PRIMARY_BLUE = new Color(37, 99, 235);    // #2563EB
    public static final Color PRIMARY_HOVER = new Color(29, 78, 216);   // #1D4ED8
    public static final Color ACCENT_SKY = new Color(56, 189, 248);     // #38BDF8

    // Latar & Kartu
    public static final Color BG_MAIN = new Color(241, 245, 249);       // #F1F5F9
    public static final Color BG_CARD = new Color(255, 255, 255);       // #FFFFFF
    public static final Color BG_CARD_HOVER = new Color(248, 250, 255); // #F8FAFF
    public static final Color BG_BADGE_BLUE = new Color(239, 246, 255); // #EFF6FF
    public static final Color BG_BADGE_HOVER = new Color(219, 234, 254);// #DBEAFE

    // Garis & Teks
    public static final Color BORDER_SOFT = new Color(226, 232, 240);   // #E2E8F0
    public static final Color BORDER_INPUT = new Color(203, 213, 225);  // #CBD5E1
    public static final Color TEXT_PRIMARY = new Color(15, 23, 42);     // #0F172A
    public static final Color TEXT_BODY = new Color(51, 65, 85);        // #334155
    public static final Color TEXT_SECONDARY = new Color(100, 116, 139);// #64748B
    public static final Color TEXT_ON_NAVY = new Color(148, 163, 184);  // #94A3B8

    // Warna Aksi Semantik (untuk tombol CRUD)
    public static final Color SUCCESS = new Color(22, 163, 74);         // #16A34A
    public static final Color WARNING = new Color(217, 119, 6);         // #D97706
    public static final Color DANGER = new Color(220, 38, 38);          // #DC2626
    public static final Color NEUTRAL = new Color(100, 116, 139);       // #64748B

    // Tipografi Standar
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);

    private static boolean initialized = false;
    private static final Map<String, ImageIcon> ICON_CACHE = new HashMap<String, ImageIcon>();

    private UITheme() {
    }

    /**
     * Memasang ikon logo sekolah SMP 259 Jakarta pada title bar & taskbar jendela.
     */
    public static void applyWindowIcon(java.awt.Window window) {
        if (window == null) {
            return;
        }
        ImageIcon icon = getScaledIcon("/resource/logosekolah.png", 64, 64);
        if (icon != null) {
            window.setIconImage(icon.getImage());
        }
    }

    /**
     * Inisialisasi tema global FlatLaf beserta properti sudut membulat & fokus.
     */
    public static void setup() {
        if (initialized && UIManager.getLookAndFeel() instanceof FlatLightLaf) {
            return;
        }
        try {
            FlatLightLaf.setup();
            UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));
            UIManager.put("Button.arc", Integer.valueOf(10));
            UIManager.put("Component.arc", Integer.valueOf(10));
            UIManager.put("TextComponent.arc", Integer.valueOf(10));
            UIManager.put("CheckBox.arc", Integer.valueOf(6));
            UIManager.put("Component.focusWidth", Integer.valueOf(1));
            UIManager.put("Component.innerFocusWidth", Integer.valueOf(0));
            UIManager.put("Component.focusedBorderColor", PRIMARY_BLUE);
            UIManager.put("PasswordField.showRevealButton", Boolean.TRUE);
            UIManager.put("ScrollBar.thumbArc", Integer.valueOf(999));
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
            UIManager.put("ScrollBar.width", Integer.valueOf(10));
            UIManager.put("Table.rowHeight", Integer.valueOf(34));
            UIManager.put("Table.showHorizontalLines", Boolean.TRUE);
            UIManager.put("Table.showVerticalLines", Boolean.FALSE);
            UIManager.put("Table.selectionBackground", new Color(219, 234, 254));
            UIManager.put("Table.selectionForeground", TEXT_PRIMARY);
            UIManager.put("TableHeader.height", Integer.valueOf(38));
            UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 13));
            UIManager.put("TableHeader.background", new Color(248, 250, 252));
            UIManager.put("TableHeader.foreground", TEXT_BODY);
            initialized = true;
        } catch (Exception ex) {
            System.err.println("UITheme gagal memuat FlatLaf: " + ex.getMessage());
        }
    }

    /**
     * Memuat dan mengubah ukuran ikon secara tajam (progressive bilinear) dengan cache.
     */
    public static ImageIcon getScaledIcon(String resourcePath, int targetW, int targetH) {
        if (resourcePath == null || targetW <= 0 || targetH <= 0) {
            return null;
        }
        String key = resourcePath + "@" + targetW + "x" + targetH;
        ImageIcon cached = ICON_CACHE.get(key);
        if (cached != null) {
            return cached;
        }
        try {
            URL url = UITheme.class.getResource(resourcePath);
            if (url == null) {
                return null;
            }
            BufferedImage src = ImageIO.read(url);
            if (src == null) {
                return null;
            }
            double ratio = Math.min((double) targetW / src.getWidth(), (double) targetH / src.getHeight());
            int finalW = Math.max(1, (int) Math.round(src.getWidth() * ratio));
            int finalH = Math.max(1, (int) Math.round(src.getHeight() * ratio));

            BufferedImage current = src;
            int w = src.getWidth();
            int h = src.getHeight();
            while (w / 2 >= finalW && h / 2 >= finalH) {
                w /= 2;
                h /= 2;
                BufferedImage tmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = tmp.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.drawImage(current, 0, 0, w, h, null);
                g2.dispose();
                current = tmp;
            }
            BufferedImage resized = new BufferedImage(finalW, finalH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resized.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(current, 0, 0, finalW, finalH, null);
            g2.dispose();

            ImageIcon result = new ImageIcon(resized);
            ICON_CACHE.put(key, result);
            return result;
        } catch (Exception ex) {
            System.err.println("Gagal memuat ikon " + resourcePath + ": " + ex.getMessage());
            return null;
        }
    }

    /**
     * Menerapkan gaya kartu putih modern bersudut membulat.
     */
    public static void styleCard(JPanel panel) {
        if (panel == null) {
            return;
        }
        panel.setOpaque(false);
        panel.setBackground(BG_CARD);
        panel.setBorder(null);
        panel.putClientProperty("FlatLaf.style",
                "arc: 18; background: #FFFFFF; border: 1,1,1,1,#E2E8F0,1,18");
    }

    /**
     * Menerapkan gaya wadah ikon membulat (badge).
     */
    public static void styleIconBadge(JPanel badge, String hexBg, int arc) {
        if (badge == null) {
            return;
        }
        badge.setOpaque(false);
        badge.setBorder(null);
        badge.putClientProperty("FlatLaf.style",
                "arc: " + arc + "; background: " + hexBg);
    }

    /**
     * Menerapkan gaya input teks modern dengan placeholder dan tombol hapus cepat.
     */
    public static void styleTextField(JTextField field, String placeholder) {
        if (field == null) {
            return;
        }
        field.setFont(FONT_INPUT);
        field.setForeground(TEXT_PRIMARY);
        field.setBackground(BG_CARD);
        field.putClientProperty("JTextField.placeholderText", placeholder);
        field.putClientProperty("JTextField.showClearButton", Boolean.TRUE);
        field.putClientProperty("FlatLaf.style",
                "arc: 10; margin: 6,12,6,12; borderColor: #CBD5E1; focusedBorderColor: #2563EB");
    }

    /**
     * Menerapkan gaya input password modern dengan placeholder dan tombol lihat sandi.
     */
    public static void stylePasswordField(JPasswordField field, String placeholder) {
        if (field == null) {
            return;
        }
        field.setFont(FONT_INPUT);
        field.setForeground(TEXT_PRIMARY);
        field.setBackground(BG_CARD);
        field.putClientProperty("JTextField.placeholderText", placeholder);
        field.putClientProperty("FlatLaf.style",
                "arc: 10; margin: 6,12,6,12; borderColor: #CBD5E1; focusedBorderColor: #2563EB; showRevealButton: true");
    }

    /**
     * Menerapkan gaya tombol utama (Royal Blue CTA).
     */
    public static void stylePrimaryButton(final JButton button) {
        if (button == null) {
            return;
        }
        button.setFont(FONT_BUTTON);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_BLUE);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.putClientProperty("FlatLaf.style",
                "arc: 10; background: #2563EB; hoverBackground: #1D4ED8; pressedBackground: #1E40AF; "
                + "foreground: #FFFFFF; borderWidth: 0; focusWidth: 0");
    }

    /**
     * Menerapkan gaya tombol sekunder (Outline Slate bersih).
     */
    public static void styleSecondaryButton(final JButton button) {
        if (button == null) {
            return;
        }
        button.setBorder(UIManager.getBorder("Button.border"));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(TEXT_BODY);
        button.setBackground(BG_CARD);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.putClientProperty("FlatLaf.style",
                "arc: 10; background: #FFFFFF; hoverBackground: #F1F5F9; pressedBackground: #E2E8F0; "
                + "foreground: #334155; borderColor: #CBD5E1; borderWidth: 1; focusWidth: 0");
    }

    /**
     * Membuat seluruh permukaan kartu portal interaktif saat di-hover dan diklik.
     */
    public static void makePortalCardInteractive(
            final JPanel card,
            final JPanel badge,
            final JButton actionBtn,
            final Runnable onActivate) {
        if (card == null) {
            return;
        }
        styleCard(card);
        if (badge != null) {
            styleIconBadge(badge, "#EFF6FF", 24);
        }
        if (actionBtn != null) {
            stylePrimaryButton(actionBtn);
        }

        final MouseAdapter cardAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.putClientProperty("FlatLaf.style",
                        "arc: 18; background: #F8FAFF; border: 1,1,1,1,#2563EB,1,18");
                if (badge != null) {
                    badge.putClientProperty("FlatLaf.style", "arc: 24; background: #DBEAFE");
                }
                card.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                java.awt.Point p = javax.swing.SwingUtilities.convertPoint(
                        e.getComponent(), e.getPoint(), card);
                if (p.x >= 0 && p.y >= 0 && p.x < card.getWidth() && p.y < card.getHeight()) {
                    return;
                }
                card.putClientProperty("FlatLaf.style",
                        "arc: 18; background: #FFFFFF; border: 1,1,1,1,#E2E8F0,1,18");
                if (badge != null) {
                    badge.putClientProperty("FlatLaf.style", "arc: 24; background: #EFF6FF");
                }
                card.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (onActivate != null && !(e.getSource() instanceof JButton)) {
                    onActivate.run();
                }
            }
        };

        attachRecursive(card, cardAdapter);
    }

    private static void attachRecursive(Component comp, MouseAdapter adapter) {
        comp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        comp.addMouseListener(adapter);
        if (comp instanceof Container) {
            Component[] children = ((Container) comp).getComponents();
            for (int i = 0; i < children.length; i++) {
                attachRecursive(children[i], adapter);
            }
        }
    }

    /**
     * Merapikan tampilan JTable modern dengan zebra-striping halus (aman untuk kolom Checkbox Boolean).
     */
    public static void styleTable(JTable table, JScrollPane scrollPane) {
        if (table == null) {
            return;
        }
        table.setRowHeight(34);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(TEXT_PRIMARY);
        table.setBackground(Color.WHITE);
        table.setGridColor(BORDER_SOFT);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setFillsViewportHeight(true);

        DefaultTableCellRenderer stripedRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                if (isSelected) {
                    c.setBackground(new Color(219, 234, 254));
                    c.setForeground(TEXT_PRIMARY);
                } else {
                    c.setBackground((row % 2 == 0) ? Color.WHITE : new Color(248, 250, 252));
                    c.setForeground(TEXT_PRIMARY);
                }
                return c;
            }
        };
        table.setDefaultRenderer(Object.class, stripedRenderer);
        table.setDefaultRenderer(String.class, stripedRenderer);
        table.setDefaultRenderer(Number.class, stripedRenderer);

        JTableHeader header = table.getTableHeader();
        if (header != null) {
            header.setFont(new Font("Segoe UI", Font.BOLD, 12));
            header.setBackground(new Color(248, 250, 252));
            header.setForeground(TEXT_BODY);
            header.setReorderingAllowed(false);
            Border bottomLine = BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SOFT);
            header.setBorder(bottomLine);
            if (header.getDefaultRenderer() instanceof DefaultTableCellRenderer) {
                ((DefaultTableCellRenderer) header.getDefaultRenderer())
                        .setHorizontalAlignment(SwingConstants.LEFT);
            }
        }
        if (scrollPane != null) {
            scrollPane.getViewport().setBackground(Color.WHITE);
            scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_SOFT, 1));
            if (scrollPane.getColumnHeader() == null && header != null) {
                scrollPane.setColumnHeaderView(header);
            }
        }
    }

    /**
     * Menyiapkan JInternalFrame tanpa border/titlebar bawaan dengan latar abu-abu lembut.
     */
    public static void prepareInternalFrame(JInternalFrame frame) {
        if (frame == null) {
            return;
        }
        frame.setBorder(null);
        frame.getContentPane().setBackground(BG_MAIN);
        if (frame.getUI() instanceof javax.swing.plaf.basic.BasicInternalFrameUI) {
            ((javax.swing.plaf.basic.BasicInternalFrameUI) frame.getUI()).setNorthPane(null);
        }
    }

    /**
     * Menerapkan gaya tombol aksi CRUD semantik:
     * variant: "SAVE", "EDIT", "DELETE", "RESET", "PRINT", "SEARCH"
     */
    public static void styleActionButton(JButton button, String variant, String iconPath) {
        if (button == null) {
            return;
        }
        button.setBorder(UIManager.getBorder("Button.border"));
        if (iconPath != null) {
            ImageIcon ic = getScaledIcon(iconPath, 16, 16);
            if (ic != null) {
                button.setIcon(ic);
                button.setIconTextGap(8);
            }
        }
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.CENTER);

        String v = (variant == null) ? "PRIMARY" : variant.toUpperCase();
        if ("SAVE".equals(v) || "SUCCESS".equals(v)) {
            button.setForeground(Color.WHITE);
            button.putClientProperty("FlatLaf.style",
                    "arc: 10; margin: 6,14,6,14; background: #16A34A; hoverBackground: #15803D; "
                    + "pressedBackground: #166534; foreground: #FFFFFF; borderWidth: 0; focusWidth: 0");
        } else if ("DELETE".equals(v) || "DANGER".equals(v)) {
            button.setForeground(Color.WHITE);
            button.putClientProperty("FlatLaf.style",
                    "arc: 10; margin: 6,14,6,14; background: #DC2626; hoverBackground: #B91C1C; "
                    + "pressedBackground: #991B1B; foreground: #FFFFFF; borderWidth: 0; focusWidth: 0");
        } else if ("RESET".equals(v) || "SECONDARY".equals(v)) {
            button.setForeground(TEXT_BODY);
            button.putClientProperty("FlatLaf.style",
                    "arc: 10; margin: 6,14,6,14; background: #FFFFFF; hoverBackground: #F1F5F9; "
                    + "pressedBackground: #E2E8F0; foreground: #334155; borderColor: #CBD5E1; borderWidth: 1; focusWidth: 0");
        } else if ("PRINT".equals(v) || "NAVY".equals(v)) {
            button.setForeground(Color.WHITE);
            button.putClientProperty("FlatLaf.style",
                    "arc: 10; margin: 6,14,6,14; background: #0F172A; hoverBackground: #1E293B; "
                    + "pressedBackground: #334155; foreground: #FFFFFF; borderWidth: 0; focusWidth: 0");
        } else if ("SOFT".equals(v)) {
            button.setForeground(PRIMARY_BLUE);
            button.putClientProperty("FlatLaf.style",
                    "arc: 10; margin: 6,12,6,12; background: #EFF6FF; hoverBackground: #DBEAFE; "
                    + "pressedBackground: #BFDBFE; foreground: #1D4ED8; borderColor: #BFDBFE; borderWidth: 1; focusWidth: 0");
        } else {
            // EDIT / PRIMARY / SEARCH
            button.setForeground(Color.WHITE);
            button.putClientProperty("FlatLaf.style",
                    "arc: 10; margin: 6,14,6,14; background: #2563EB; hoverBackground: #1D4ED8; "
                    + "pressedBackground: #1E40AF; foreground: #FFFFFF; borderWidth: 0; focusWidth: 0");
        }
    }

    /**
     * Menerapkan gaya JComboBox modern.
     */
    public static void styleComboBox(JComboBox combo) {
        if (combo == null) {
            return;
        }
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setForeground(TEXT_PRIMARY);
        combo.setBackground(Color.WHITE);
        combo.putClientProperty("FlatLaf.style",
                "arc: 10; padding: 4,8,4,8; borderColor: #CBD5E1; focusedBorderColor: #2563EB");
    }

    /**
     * Menerapkan gaya JSpinner modern.
     */
    public static void styleSpinner(JSpinner spinner) {
        if (spinner == null) {
            return;
        }
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        spinner.putClientProperty("FlatLaf.style",
                "arc: 10; borderColor: #CBD5E1; focusedBorderColor: #2563EB");
    }

    /**
     * Menerapkan gaya JTextArea di dalam JScrollPane modern.
     */
    public static void styleTextArea(JTextArea area, JScrollPane scroll, String placeholder) {
        if (area != null) {
            area.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            area.setForeground(TEXT_PRIMARY);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setMargin(new Insets(8, 10, 8, 10));
            if (placeholder != null) {
                area.putClientProperty("JTextField.placeholderText", placeholder);
            }
        }
        if (scroll != null) {
            scroll.setBorder(UIManager.getBorder("ScrollPane.border"));
            scroll.putClientProperty("FlatLaf.style",
                    "arc: 10; borderColor: #CBD5E1; focusedBorderColor: #2563EB");
        }
    }

    /**
     * Merapikan komponen JDateChooser (JCalendar) agar serasi dengan input FlatLaf.
     */
    public static void styleDateChooser(JComponent dateChooser) {
        if (dateChooser == null) {
            return;
        }
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dateChooser.setBackground(Color.WHITE);
        Component[] comps = dateChooser.getComponents();
        for (int i = 0; i < comps.length; i++) {
            if (comps[i] instanceof JTextField) {
                JTextField tf = (JTextField) comps[i];
                tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                tf.putClientProperty("FlatLaf.style",
                        "arc: 10; margin: 4,10,4,10; borderColor: #CBD5E1; focusedBorderColor: #2563EB");
            } else if (comps[i] instanceof JButton) {
                JButton b = (JButton) comps[i];
                b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }
    }

    /**
     * Menerapkan gaya tombol navigasi sidebar gelap (Navy) beserta indikator aktif.
     */
    public static void styleSidebarItem(JButton button, String iconPath, boolean active) {
        if (button == null) {
            return;
        }
        button.setBorder(UIManager.getBorder("Button.border"));
        if (iconPath != null) {
            ImageIcon ic = getScaledIcon(iconPath, 18, 18);
            if (ic != null) {
                button.setIcon(ic);
            }
        }
        button.setFont(new Font("Segoe UI", active ? Font.BOLD : Font.PLAIN, 13));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(12);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(button.isEnabled() ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
        applySidebarState(button, active);
    }

    /**
     * Mengubah status aktif di antara daftar tombol navigasi sidebar.
     */
    public static void setActiveSidebar(JButton activeBtn, JButton[] allButtons) {
        if (allButtons == null) {
            return;
        }
        for (int i = 0; i < allButtons.length; i++) {
            JButton b = allButtons[i];
            if (b != null) {
                boolean isAct = (b == activeBtn);
                b.setFont(new Font("Segoe UI", isAct ? Font.BOLD : Font.PLAIN, 13));
                applySidebarState(b, isAct);
            }
        }
    }

    private static void applySidebarState(JButton button, boolean active) {
        if (!button.isEnabled()) {
            button.setForeground(new Color(100, 116, 139));
            button.putClientProperty("FlatLaf.style",
                    "arc: 10; margin: 6,14,6,12; background: #0F172A; "
                    + "disabledText: #475569; borderWidth: 0; focusWidth: 0");
            return;
        }
        if (active) {
            button.setForeground(Color.WHITE);
            button.putClientProperty("FlatLaf.style",
                    "arc: 10; margin: 6,14,6,12; background: #2563EB; hoverBackground: #1D4ED8; "
                    + "pressedBackground: #1E40AF; foreground: #FFFFFF; borderWidth: 0; focusWidth: 0");
        } else {
            button.setForeground(new Color(203, 213, 225));
            button.putClientProperty("FlatLaf.style",
                    "arc: 10; margin: 6,14,6,12; background: #0F172A; hoverBackground: #1E293B; "
                    + "pressedBackground: #334155; foreground: #CBD5E1; borderWidth: 0; focusWidth: 0");
        }
    }

    /**
     * Menerapkan gaya tombol keluar (Logout) pada bagian bawah sidebar.
     */
    public static void styleSidebarLogout(JButton button, String iconPath) {
        if (button == null) {
            return;
        }
        button.setBorder(UIManager.getBorder("Button.border"));
        if (iconPath != null) {
            ImageIcon ic = getScaledIcon(iconPath, 18, 18);
            if (ic != null) {
                button.setIcon(ic);
            }
        }
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(new Color(252, 165, 165));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(12);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.putClientProperty("FlatLaf.style",
                "arc: 10; margin: 6,14,6,12; background: #1E293B; hoverBackground: #DC2626; "
                + "pressedBackground: #B91C1C; foreground: #FCA5A5; borderWidth: 0; focusWidth: 0");
    }

    /**
     * Menerapkan gaya lencana profil pengguna di bilah atas (Top Header).
     */
    public static void styleUserProfileBadge(JPanel badge) {
        if (badge == null) {
            return;
        }
        badge.setOpaque(false);
        badge.setBorder(null);
        badge.putClientProperty("FlatLaf.style",
                "arc: 12; background: #F8FAFC; border: 1,1,1,1,#E2E8F0,1,12");
    }

    /**
     * Membuat panel grafik Donut/Ring modern yang tajam dengan JFreeChart.
     */
    public static ChartPanel createModernRingChartPanel(
            DefaultPieDataset dataset,
            Color[] palette,
            int prefW,
            int prefH) {
        JFreeChart chart = ChartFactory.createRingChart(null, dataset, true, true, false);
        chart.setAntiAlias(true);
        chart.setTextAntiAlias(true);
        chart.setBackgroundPaint(Color.WHITE);
        chart.setPadding(new RectangleInsets(4, 4, 4, 4));

        if (chart.getLegend() != null) {
            chart.getLegend().setPosition(RectangleEdge.BOTTOM);
            chart.getLegend().setBackgroundPaint(Color.WHITE);
            chart.getLegend().setItemPaint(TEXT_BODY);
            chart.getLegend().setItemFont(new Font("Segoe UI", Font.BOLD, 12));
            chart.getLegend().setFrame(org.jfree.chart.block.BlockBorder.NONE);
            chart.getLegend().setPadding(new RectangleInsets(6, 0, 2, 0));
        }

        RingPlot plot = (RingPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);
        plot.setSectionDepth(0.42);
        plot.setSeparatorsVisible(true);
        plot.setSeparatorPaint(Color.WHITE);
        plot.setSeparatorStroke(new BasicStroke(2.5f));
        plot.setBaseSectionOutlinePaint(Color.WHITE);
        plot.setBaseSectionOutlineStroke(new BasicStroke(2.0f));
        plot.setSimpleLabels(false);
        plot.setLabelFont(new Font("Segoe UI", Font.BOLD, 11));
        plot.setLabelPaint(TEXT_BODY);
        plot.setLabelBackgroundPaint(new Color(248, 250, 252));
        plot.setLabelOutlinePaint(BORDER_SOFT);
        plot.setLabelShadowPaint(null);
        plot.setLabelLinkPaint(BORDER_INPUT);
        plot.setLabelLinkStroke(new BasicStroke(1.2f));
        plot.setInteriorGap(0.04);

        if (palette != null && palette.length > 0) {
            for (int i = 0; i < dataset.getItemCount(); i++) {
                Comparable key = dataset.getKey(i);
                Color c = palette[i % palette.length];
                plot.setSectionPaint(key, c);
            }
        }

        ChartPanel cp = new ChartPanel(chart);
        cp.setBackground(Color.WHITE);
        cp.setOpaque(false);
        cp.setPopupMenu(null);
        cp.setDomainZoomable(false);
        cp.setRangeZoomable(false);
        if (prefW > 0 && prefH > 0) {
            cp.setPreferredSize(new java.awt.Dimension(prefW, prefH));
        }
        return cp;
    }
}
