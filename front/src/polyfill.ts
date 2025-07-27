/***************************************************************************************************
 * Polyfills Angular de base
 * ------------------------------------------------------------------
 * - Zone JS est obligatoire
 * - $localize est nécessaire si tu fais de l’i18n
 **************************************************************************************************/
import 'zone.js'; // ← indispensable

/***************************************************************************************************
 * Polyfills applicatifs
 * ------------------------------------------------------------------
 * Ici on expose des symboles Node que certaines libs (SockJS, STOMP…)
 * attendent encore dans le navigateur.
 **************************************************************************************************/
(window as any).global ??= window; // ← corrige « global is not defined »
