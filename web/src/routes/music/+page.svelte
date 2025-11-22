<script lang="ts">
	import { onMount } from 'svelte';

	let selectedGuild = '123456789'; // Default guild for demo
	let playerState: any = null;
	let queue: any[] = [];
	let loading = true;

	async function loadMusicData() {
		try {
			const [playerRes, queueRes] = await Promise.all([
				fetch(`/api/guilds/${selectedGuild}/music/player`),
				fetch(`/api/guilds/${selectedGuild}/music/queue`)
			]);

			if (playerRes.ok) {
				playerState = await playerRes.json();
			}

			if (queueRes.ok) {
				const data = await queueRes.json();
				queue = data.queue || [];
			}
		} catch (error) {
			console.error('Failed to load music data:', error);
		} finally {
			loading = false;
		}
	}

	onMount(() => {
		loadMusicData();
		// Poll for updates every 5 seconds
		const interval = setInterval(loadMusicData, 5000);
		return () => clearInterval(interval);
	});

	function formatDuration(ms: number): string {
		const minutes = Math.floor(ms / 60000);
		const seconds = Math.floor((ms % 60000) / 1000);
		return `${minutes}:${seconds.toString().padStart(2, '0')}`;
	}
</script>

<div class="max-w-7xl mx-auto">
	<div class="mb-8">
		<h1 class="text-4xl font-bold bg-gradient-to-r from-cyber-blue to-cyber-purple bg-clip-text text-transparent">
			Music Control
		</h1>
		<p class="text-gray-400 mt-2">Manage music playback across your servers</p>
	</div>

	{#if loading}
		<div class="text-center py-12">
			<div class="inline-block w-8 h-8 border-4 border-cyber-blue border-t-transparent rounded-full animate-spin"></div>
			<p class="text-gray-400 mt-4">Loading music player...</p>
		</div>
	{:else}
		<!-- Now Playing Card -->
		<div class="bg-gradient-to-br from-gray-900 to-gray-800 rounded-xl border border-gray-700 mb-8 overflow-hidden">
			<div class="p-8">
				<h2 class="text-2xl font-bold mb-6">Now Playing</h2>

				{#if playerState?.currentTrack}
					<div class="flex gap-6">
						<div class="w-48 h-48 rounded-lg bg-gradient-to-br from-cyber-blue to-cyber-purple flex items-center justify-center">
							<span class="text-6xl">🎵</span>
						</div>

						<div class="flex-1">
							<h3 class="text-2xl font-bold">{playerState.currentTrack.title}</h3>
							<p class="text-gray-400 text-lg mt-2">{playerState.currentTrack.author}</p>

							<div class="mt-6">
								<div class="flex justify-between text-sm text-gray-400 mb-2">
									<span>{formatDuration(playerState.positionMs)}</span>
									<span>{formatDuration(playerState.currentTrack.durationMs)}</span>
								</div>
								<div class="h-2 bg-gray-700 rounded-full overflow-hidden">
									<div
										class="h-full bg-gradient-to-r from-cyber-blue to-cyber-purple"
										style="width: {(playerState.positionMs / playerState.currentTrack.durationMs) * 100}%"
									></div>
								</div>
							</div>

							<div class="flex items-center gap-4 mt-6">
								<button class="w-12 h-12 rounded-full bg-gray-700 hover:bg-gray-600 transition-colors flex items-center justify-center">
									⏮️
								</button>
								<button class="w-16 h-16 rounded-full bg-gradient-to-r from-cyber-blue to-cyber-purple hover:shadow-lg hover:shadow-cyber-blue/50 transition-all flex items-center justify-center text-2xl">
									{playerState.isPlaying ? '⏸️' : '▶️'}
								</button>
								<button class="w-12 h-12 rounded-full bg-gray-700 hover:bg-gray-600 transition-colors flex items-center justify-center">
									⏭️
								</button>

								<div class="flex-1"></div>

								<div class="flex items-center gap-2">
									<span class="text-sm text-gray-400">🔊</span>
									<input
										type="range"
										min="0"
										max="100"
										value={playerState.volume}
										class="w-32"
									/>
									<span class="text-sm text-gray-400 w-8">{playerState.volume}%</span>
								</div>
							</div>
						</div>
					</div>
				{:else}
					<div class="text-center py-12">
						<div class="text-6xl mb-4">🎵</div>
						<p class="text-gray-400">No track playing</p>
						<p class="text-sm text-gray-500 mt-2">Queue a song to start playing music</p>
					</div>
				{/if}
			</div>
		</div>

		<!-- Queue -->
		<div class="bg-gradient-to-br from-gray-900 to-gray-800 rounded-xl border border-gray-700">
			<div class="p-6 border-b border-gray-700">
				<h2 class="text-2xl font-bold">Queue ({queue.length} tracks)</h2>
			</div>

			<div class="p-6">
				{#if queue.length === 0}
					<div class="text-center py-12">
						<p class="text-gray-400">Queue is empty</p>
					</div>
				{:else}
					<div class="space-y-2">
						{#each queue as track, i}
							<div class="flex items-center gap-4 p-4 rounded-lg bg-gray-800 hover:bg-gray-750 transition-colors">
								<span class="text-gray-400 w-8">{i + 1}</span>
								<div class="flex-1">
									<p class="font-medium">{track.title}</p>
									<p class="text-sm text-gray-400">{track.author}</p>
								</div>
								<span class="text-gray-400">{formatDuration(track.durationMs)}</span>
								<button class="text-gray-400 hover:text-red-500 transition-colors">🗑️</button>
							</div>
						{/each}
					</div>
				{/if}
			</div>
		</div>
	{/if}
</div>
