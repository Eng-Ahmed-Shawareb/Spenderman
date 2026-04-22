# Design System Specification: Premium Neon Glassmorphism

## 1. Overview & Creative North Star
**Creative North Star: "The Synthetic Void"**
This design system is built to transcend the "utility app" aesthetic, transforming a desktop financial tool into a high-end digital artifact. We are moving away from the rigid, boxed-in layouts of traditional enterprise software. Instead, we embrace **The Synthetic Void**: an expansive, deep-space environment where data doesn't just sit on a screen—it glows within it. 

By leveraging intentional asymmetry, oversized display typography, and layered translucency, we create an experience that feels like a premium heads-up display (HUD). We break the "template" look by treating the 1920x1080 canvas as a singular dark landscape where light (data) defines the geography.

---

## 2. Colors & Surface Logic
The palette is rooted in a high-contrast dark mode where the background is an absolute, and accents are treated as light sources.

### The "No-Line" Rule
Traditional UI relies on 1px solid borders to separate sections. **This is prohibited here.** Boundaries must be defined through:
1.  **Background Shifts:** Using `surface_container_low` against the `surface` background.
2.  **Tonal Transitions:** Subtle shifts in container depth.
3.  **Negative Space:** Using generous padding to imply grouping.

### Surface Hierarchy & Nesting
Treat the UI as a series of nested physical layers. 
*   **Base Layer:** `surface` (#0a0e18) – The foundation.
*   **Layout Sections:** `surface_container_low` (#0f131e) – Large sidebars or background panels.
*   **Interactive Cards:** `surface_container_highest` (#202534) with 40-60% opacity and a `backdrop-blur` (20px-40px).
*   **Floating Elements:** `surface_bright` (#262c3c) – For tooltips or high-level popovers.

### The "Glass & Gradient" Rule
To achieve "Soul," use subtle linear gradients (45-degree angle) transitioning from `primary` (#69daff) to `primary_container` (#00cffc) for main CTAs. This creates a "glowing filament" effect rather than a flat plastic button.

---

## 3. Typography
We use a dual-font strategy to balance futuristic precision with human readability.

*   **Display & Headlines (Space Grotesk):** This is our "Technical" voice. The geometric quirks of Space Grotesk at `display-lg` (3.5rem) or `headline-lg` (2rem) provide the futuristic, editorial edge. Use it for big numbers and section headers.
*   **Body & Titles (Manrope):** Our "Functional" voice. Manrope is highly legible at small sizes. Use `body-md` (0.875rem) for the majority of data entries to ensure the UI feels grounded.

**Hierarchy Note:** Always lead with a massive `display` token for account balances. The contrast between a 3.5rem balance and a 0.75rem `label-md` creates the "high-end editorial" tension we desire.

---

## 4. Elevation & Depth
Depth is not achieved through shadows, but through **Luminance and Blur**.

*   **The Layering Principle:** Stack `surface_container` tiers. Place a `surface_container_highest` element inside a `surface_container_low` area to create a soft, natural lift.
*   **Ambient Shadows:** If a floating effect is required (e.g., a modal), use a shadow color tinted with `primary` at 4% opacity with a 60px blur. It should look like a glow, not a shadow.
*   **The "Ghost Border":** For glass cards, use the `outline_variant` token at 15% opacity. This 1px "rim light" defines the edge of the glass without breaking the "No-Line" rule.
*   **Glassmorphism:** Apply a `backdrop-blur` to all cards. This allows the deep navy of the background to bleed through, softening the interface and making it feel like a single cohesive environment.

---

## 5. Components

### Buttons
*   **Primary:** Gradient fill (`primary` to `primary_container`). Roundedness `md` (0.75rem). No border. White text (`on_primary_container`).
*   **Secondary:** Ghost style. `outline_variant` border at 20% opacity. Text in `primary`.
*   **Tertiary:** Text only in `primary_dim`. High letter-spacing for `label-md`.

### Input Fields
*   **Default:** No background fill. Only a "Ghost Border" bottom-line using `outline_variant`.
*   **Focus State:** The bottom-line transitions to `primary` with a 2px outer glow (neon effect). Label moves to `label-sm` above the field.

### Cards (The "Glass" Container)
*   Forbid divider lines inside cards. Use `surface_container_high` for a sub-section within a card to create a "recessed" look.
*   **Roundedness:** Use `xl` (1.5rem) for main dashboard cards to emphasize a premium, friendly feel.

### Financial Indicators (Color Logic)
*   **Income:** Use `primary` (Electric Cyan). It represents growth and clarity.
*   **Expenses:** Use `error` (#ff716c) (Neon Red).
*   **Neutral/Savings:** Use `secondary` (Magenta).

### Sidebar (Desktop Specific)
*   Avoid a solid-color sidebar. Use `surface_container_low` at 80% opacity with a heavy backdrop blur. 
*   Active states should use a "Vertical Glow"—a 2px thick `primary` line on the left edge of the active menu item, with no background change.

---

## 6. Do's and Don'ts

### Do:
*   **Use Asymmetry:** Place a large headline on the left and a small "Glass" widget on the right. White space is a luxury; use it.
*   **Layer your blurs:** If a card is on top of another card, increase the blur radius of the top-most card.
*   **Use Neon Sparingly:** If everything glows, nothing glows. Reserve high-saturation `primary` and `secondary` for data points and CTAs.

### Don't:
*   **Don't use 100% Opacity Borders:** This instantly kills the glassmorphism effect and makes the app look like a legacy Java application.
*   **Don't use Pure Grey:** Every "Neutral" color in this system is slightly blue-tinted. Pure grey (#808080) will look "muddy" against the deep navy background.
*   **Don't crowd the grid:** JavaFX layouts often default to tight grids. Override this with `lg` (1rem) and `xl` (1.5rem) spacing tokens to let the data breathe.