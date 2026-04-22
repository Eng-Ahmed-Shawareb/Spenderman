# Design System Specification: The Nocturnal Utility

## 1. Overview & Creative North Star
**Creative North Star: The Obsidian Executive**

This design system is engineered to move beyond the generic "SaaS Dashboard" aesthetic, leaning instead into a high-end, desktop-first utility experience. It rejects the sterility of white-label interfaces in favor of a deep, cinematic environment that prioritizes focus and visual depth. 

The system achieves a "custom-built" feel through the use of **Nocturnal Layering**—using varying depths of navy and slate to create hierarchy rather than lines—and **Lucent Accents**, where a singular vibrant blue acts as the guiding light for the user's journey. By embracing asymmetry in its card-based layouts and utilizing glassmorphism for modal overlays, the interface feels less like a website and more like a premium, native OS application.

---

## 2. Colors: Depth and Luminance

The palette is anchored in deep, muted navy tones, providing a sophisticated foundation for high-contrast data visualization.

### The "No-Line" Rule
To maintain a premium feel, **1px solid borders for sectioning are prohibited.** Boundaries must be defined solely through background color shifts. Use `surface_container_low` against `surface` to denote section changes.

### Surface Hierarchy & Nesting
Depth is achieved through the intentional stacking of material tiers:
- **Base Layer:** `surface` (#0b1326) - The canvas of the application.
- **Sectioning:** `surface_container_low` (#131b2e) - Used for sidebars or secondary content areas.
- **Primary Content:** `surface_container` (#171f33) - The default card background.
- **Elevated Interactive:** `surface_container_high` (#222a3d) - Used for hover states or active selection.

### The "Glass & Gradient" Rule
Floating elements (Modals, Tooltips) should utilize a backdrop blur of `20px` and a background color of `surface_variant` (#2d3449) at 70% opacity. For primary CTAs, a subtle linear gradient from `primary` (#adc6ff) to `primary_container` (#4d8eff) is encouraged to provide a "tactile" glow.

---

## 3. Typography: Editorial Clarity

The system utilizes **Inter** as its primary typeface, leveraging its mathematical precision to convey authority and modernism.

*   **Display (Lg/Md):** Reserved for high-impact data points (e.g., total balance). Use `on_surface` with a tracking of `-0.02em` for a tighter, editorial feel.
*   **Headlines & Titles:** Used for card titles and section headers. These provide the structural "bones" of the layout.
*   **Body (Md/Sm):** Optimized for readability against dark backgrounds. Ensure `on_surface_variant` is used for secondary text to reduce eye fatigue.
*   **Labels:** All-caps or smaller-scale text used for metadata and button labels, providing a technical, functional tone.

---

## 4. Elevation & Depth: Tonal Layering

Traditional drop shadows are largely discarded in favor of **Tonal Layering**. 

*   **The Layering Principle:** Place a `surface_container_lowest` (#060e20) input field inside a `surface_container` (#171f33) card to create a "recessed" effect. Conversely, use `surface_container_highest` for a "lifted" interactive element.
*   **Ambient Shadows:** When a modal requires a shadow, it must be an extra-diffused "glow." Use a blur of `40px`, an spread of `-10px`, and a color derived from `surface_tint` (#adc6ff) at 8% opacity. 
*   **The "Ghost Border":** For essential containment, use `outline_variant` at 15% opacity. This provides a "suggestion" of a boundary without the harshness of a solid line.
*   **Glassmorphism:** Use `backdrop-filter: blur(12px)` on all modal dialogs. This allows the vibrant colors of underlying donut charts or progress rings to bleed through, integrating the UI into a singular cohesive environment.

---

## 5. Components

### Buttons
*   **Primary:** Filled with `primary_container`. High-contrast `on_primary_container` text. Roundedness: `md`.
*   **Secondary:** Ghost style. Transparent background with a `Ghost Border`.
*   **Warning/Destructive:** Uses `tertiary_container` (#ff5451) to provide a soft but urgent visual "stop."

### Progress & Charts
*   **Donut Charts:** Use a thick stroke with rounded caps. The "empty" track should be `surface_container_highest`. 
*   **Progress Rings:** Centralized data points should use `display-md` typography.

### Input Fields
*   **Containers:** Recessed into the surface using `surface_container_lowest`. 
*   **Focus State:** A 2px outer glow using `primary` at 30% opacity. 
*   **Validation:** Error states use `error` (#ffb4ab) for text and icons, never the full container background.

### Cards
*   **Style:** No borders. `md` or `lg` corner radius. 
*   **Separation:** Content within cards is separated by vertical white space (1.5rem to 2rem) rather than horizontal rules.

---

## 6. Do's and Don'ts

### Do
*   **Do** use `primary_fixed_dim` for icons in a sidebar to ensure they don't compete with main content headlines.
*   **Do** leverage the "roundedness scale" consistently; use `lg` for main app containers and `sm` for small utility buttons.
*   **Do** use `on_surface_variant` for helper text to maintain a clear visual hierarchy.

### Don't
*   **Don't** use pure black (#000000) or pure white (#FFFFFF). All colors must be tinted with the system's navy/blue DNA.
*   **Don't** use traditional "Drop Shadows" with 20%+ opacity. They break the glassmorphism effect.
*   **Don't** use dividers or lines to separate list items; use a subtle background shift on hover (`surface_container_high`).
*   **Don't** use high-contrast borders. If the background shift isn't visible, increase the tonal difference between `surface` tiers instead of adding a line.