/** @type {import('tailwindcss').Config} */
export default {
	content: ['./src/**/*.{html,js,svelte,ts}'],
	theme: {
		extend: {
			colors: {
				'cyber-dark': '#0a0e27',
				'cyber-blue': '#00d4ff',
				'cyber-purple': '#b026ff',
				'cyber-pink': '#ff0080',
			}
		}
	},
	plugins: []
};
