# ChordKnight Web Dashboard

SvelteKit-based web dashboard for managing the ChordKnight Discord bot.

## Features

- 🎵 Music player controls with queue management
- ♟️ Chess puzzle viewer and opening database
- ⚙️ Bot settings configuration
- 🎨 Sci-fi themed UI with Tailwind CSS

## Development

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

The app will be available at `http://localhost:5173`.

## Building

Build for production:

```bash
npm run build
```

Preview the production build:

```bash
npm run preview
```

## API Integration

The web app connects to the Ktor API server at `http://localhost:8080`. Make sure the API server is running before starting the web app.

The Vite proxy is configured to forward `/api` requests to the API server automatically.
